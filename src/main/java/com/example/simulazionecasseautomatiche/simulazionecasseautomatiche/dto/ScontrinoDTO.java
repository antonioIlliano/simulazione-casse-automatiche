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
public class ScontrinoDTO {


    private String CodiceScontrino;

    private Map<String, Float> prodotti;

    private Float totale;


}
