package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreazioneScontrinoRequest {

    private List<String> barCodeProdotti;

    private String codiceScontrino;

}
