package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REPARTO_ENUM")
public class Reparto {

    @Id
    private Long id;

    private String nomeReparto;

    @Column(name = "REPARTO_CODE")
    private String codiceReparto;

    private List<Prodotto> prodotti;
}
