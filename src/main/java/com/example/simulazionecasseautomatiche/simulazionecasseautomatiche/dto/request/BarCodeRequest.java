package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarCodeRequest {

    private String startDate;

    private String endDate;

    private String nomeProdotto;

    private String code;

}
