package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarCodeDTO {

    private String startDate;

    private String endDate;

    private String code;

    private String productName;

}
