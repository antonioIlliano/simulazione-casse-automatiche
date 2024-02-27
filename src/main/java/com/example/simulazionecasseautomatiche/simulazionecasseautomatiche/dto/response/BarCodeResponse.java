package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response;

import lombok.*;
import org.springframework.dao.DataAccessException;
import java.time.LocalDate;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarCodeResponse {

    private LocalDate startDate;

    private LocalDate endDate;

    private String code;

    private String productName;

    private int response;

}
