package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdottoDTO {

    private String nomeProdotto;

    private Integer grammatura;

    private String udm;

    private String reparto;

    private Float prezzo;

    private List<String> barCodeList;

}
