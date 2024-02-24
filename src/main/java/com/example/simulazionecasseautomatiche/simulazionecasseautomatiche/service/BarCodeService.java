package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.service;


import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.BarCodeDTO;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.dto.response.BarCodeResponse;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.BarCode;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Prodotto;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.BarCodeRepository;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository.ProdottoRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.List;

@Service
public class BarCodeService {

    @Autowired
    private BarCodeRepository repository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    public BarCodeResponse createBarCode(BarCodeDTO dto) throws Exception {

        BarCode check = repository.findByCodeIgnoreCase(dto.getCode());
        if(Objects.nonNull(check)){
            throw new Exception("BarCode Already Exists");
        }

        Prodotto prodotto = prodottoRepository.findByNomeProdottoIgnoreCase(dto.getProductName());
        if(Objects.isNull(prodotto)){
            throw new Exception("Product not found");
        }



        BarCode barCode = BarCode.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .code(dto.getCode())
                .productId(prodotto)
                .build();

        repository.save(barCode);

        List<BarCode> barCodeList = prodotto.getBarCodeIds();
        barCodeList.add(barCode);
        prodotto.setBarCodeIds(barCodeList);
        prodottoRepository.save(prodotto);

        return BarCodeResponse.builder()
                .endDate(barCode.getEndDate())
                .startDate(barCode.getStartDate())
                .productName(barCode.getProductId().getNomeProdotto())
                .code(barCode.getCode())
                .response(Response.SC_OK)
                .build();
    }

    public BarCodeDTO getBarCodeByCode(String code) throws Exception{

        BarCode barCode = repository.findByCodeIgnoreCase(code);
        if(Objects.isNull(barCode)){
            throw  new Exception("BarCode Not Found" + Response.SC_INTERNAL_SERVER_ERROR);
        }


        return BarCodeDTO.builder()
                .startDate(barCode.getStartDate())
                .endDate(barCode.getEndDate())
                .code(barCode.getCode())
                .productName(barCode.getProductId().getNomeProdotto())
                .build();
    }

}
