package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.repository;

import com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity.Reparto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartoRepository extends JpaRepository<Reparto, Long> {

    Reparto findByCodiceRepartoIgnoreCase(String codice);




}
