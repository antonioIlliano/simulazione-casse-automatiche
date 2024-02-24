package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreazioneScontrinoResponse {

    private Map<String, Float> prodotti;

    private Float totale;

}
