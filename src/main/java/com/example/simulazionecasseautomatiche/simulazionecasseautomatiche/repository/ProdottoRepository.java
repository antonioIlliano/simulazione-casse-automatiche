package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.BarCode;
import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    Prodotto findByNomeProdottoIgnoreCase(String nomeProdotto);

    @Query(nativeQuery = true, value = "SELECT * FROM Prodotto WHERE prodotto_id IN :idList")
    List<Prodotto> findAllByIdIn(@Param("idList") List<Long> ids);

    List<Prodotto> findByBarCodeIdsIn(List<BarCode> barCodes);


}

