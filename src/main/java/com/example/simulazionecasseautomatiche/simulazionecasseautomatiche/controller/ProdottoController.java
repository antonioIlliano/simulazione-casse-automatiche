package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.controller;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.ProdottoDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request.ProdottoRequest;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.ProdottoResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProdottoResponse> createProduct(@RequestBody ProdottoRequest prodottoRequest)
        throws Exception{
        return ResponseEntity.ok(prodottoService.createProduct(prodottoRequest));
    }

    @GetMapping("/getProduct")
    public ResponseEntity<ProdottoDTO> getProdottoByNome(@RequestParam("nomeProdotto") String nomeProdotto)
            throws Exception {
        return ResponseEntity.ok(prodottoService.getProdottoByNome(nomeProdotto));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProdottoDTO>> getAllProducts(){
        return ResponseEntity.ok(prodottoService.getAllProducts());
    }

    @GetMapping("/getStock")
    public ResponseEntity<Map<String, Long>> getStock(@RequestParam("date") LocalDate date){
        return ResponseEntity.ok(prodottoService.calcolaStock(date));
    }

    @GetMapping("/getImportoByReparto")
    public ResponseEntity<Map<String, Float>> getImportoByReparto(@RequestParam ("data") LocalDate data){
        return ResponseEntity.ok(prodottoService.getImportoByReparto(data));
    }
    @GetMapping("/getImportoAnnuoByReparto")
    public ResponseEntity<Map<String, Float>> getImportoAnnuo(@RequestParam ("anno") Integer anno){
        return ResponseEntity.ok(prodottoService.getImportoAnnuoByReparto(anno));
    }

}
