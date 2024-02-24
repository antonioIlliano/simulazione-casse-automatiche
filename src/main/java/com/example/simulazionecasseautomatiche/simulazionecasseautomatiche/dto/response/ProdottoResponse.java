package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdottoResponse {

    private String nomeProdotto;

    private String reparto;

    private String barCode;

    private String udm;

    private Float prezzo;

    private int response;




}
