package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.controller;


import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.QuantitaIncassoDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request.CreazioneScontrinoRequest;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.CreazioneScontrinoResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service.ScontrinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipt")
public class ScontrinoController {

    @Autowired
    private ScontrinoService scontrinoService;


    @PostMapping("/createRecipt")
    public ResponseEntity<CreazioneScontrinoResponse> createRecipt(@RequestBody CreazioneScontrinoRequest request) throws Exception {
        return ResponseEntity.ok(scontrinoService.createScontrino(request));
    }

    @GetMapping("/getTotalePerGiorno")
    public ResponseEntity<Float> getTotalePerGiorno(@RequestParam ("data") LocalDate data){
        return ResponseEntity.ok(scontrinoService.getTotaleByDay(data));
    }

    @GetMapping("/getQuantitaAndIncasso")
    public ResponseEntity<List<QuantitaIncassoDTO>> getQuantitaAndIncasso(@RequestParam("data") LocalDate data){
        return ResponseEntity.ok(scontrinoService.getQuantitaAndIncasso(data));
    }


}
