package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdottoRequest {

    private String nomeProdotto;

    private Integer grammatura;

    private String udm;

    private String reparto;

    private Float prezzo;

    private Long stock;


}
