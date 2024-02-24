package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarCodeResponse {

    private String startDate;

    private String endDate;

    private String code;

    private String productName;

    private int response;

}
