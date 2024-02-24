package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuantitaIncassoDTO {

    private String nomeProdotto;

    private Long quantita;

    private Float totaleIncasso;

}
