package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.request;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarCodeRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private String nomeProdotto;

    private String code;

}
