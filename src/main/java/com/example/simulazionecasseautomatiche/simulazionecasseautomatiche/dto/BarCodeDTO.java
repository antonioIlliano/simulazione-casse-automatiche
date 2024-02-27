package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto;


import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarCodeDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private String code;

    private String productName;

}
