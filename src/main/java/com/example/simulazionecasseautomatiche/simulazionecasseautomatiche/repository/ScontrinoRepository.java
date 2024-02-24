package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Scontrino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface ScontrinoRepository extends JpaRepository<Scontrino, Long> {

    List<Scontrino> findAllByDataDiAcquisto(LocalDate date);

    @Query(nativeQuery = true, value = "SELECT * FROM scontrino where YEAR(data_di_acquisto) = :year")
    List<Scontrino> findAllByYear(@Param("year") Integer year);
}
