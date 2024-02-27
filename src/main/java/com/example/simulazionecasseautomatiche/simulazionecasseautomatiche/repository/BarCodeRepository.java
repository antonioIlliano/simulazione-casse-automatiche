package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.BarCode;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarCodeRepository extends JpaRepository<BarCode, Long> {

    BarCode findByCodeIgnoreCase(String code);

    List<BarCode> findAllByProductId(Prodotto prodottoId);


    @Query(value = "SELECT product_id FROM bar_code WHERE bar_code_code IN :barCodes", nativeQuery = true)
    List<Long> findAllIdsByBarCodeIn (@Param("barCodes") List<String> barCodes);

    List<BarCode> findByCodeIn(List<String> barCodes);

    List<BarCode> findByProductId(Prodotto productId);

}
