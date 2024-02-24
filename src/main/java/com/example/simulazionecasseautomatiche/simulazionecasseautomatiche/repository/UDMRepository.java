package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.UnitaDiMisura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UDMRepository extends JpaRepository<UnitaDiMisura, Long> {

    UnitaDiMisura findByCodiceUdmIgnoreCase(String codice);

}
