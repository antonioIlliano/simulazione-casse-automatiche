package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.controller;


import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.BarCodeDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.BarCodeResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service.BarCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/barCode")
public class BarCodeController {

    @Autowired
    private BarCodeService barCodeService;

    @PostMapping("/createBarCode")
    public ResponseEntity<BarCodeResponse> createBarCode(@RequestBody BarCodeDTO dto) throws Exception {
        return ResponseEntity.ok(barCodeService.createBarCode(dto));
    }

    @GetMapping("/getBarCode/{code}")
    public ResponseEntity<BarCodeDTO> getBarCodeByCode(@PathVariable ("code") String code) throws Exception{
        return ResponseEntity.ok(barCodeService.getBarCodeByCode(code));
    }




}
