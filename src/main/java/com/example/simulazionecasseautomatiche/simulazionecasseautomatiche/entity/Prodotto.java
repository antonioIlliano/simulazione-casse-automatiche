package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODOTTO")
@Builder
public class Prodotto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODOTTO_ID")
    private Long id;

    @Column(name = "NOME_PRODOTTO")
    private String nomeProdotto;

    @Column(name = "GRAMMATURA")
    private Integer grammatura;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "UNITA_DI_MISURA_ID") // Cambiato il nome della colonna di join per essere più espliciti
    private UnitaDiMisura udm;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "REPARTO_ID") // Cambiato il nome della colonna di join per essere più espliciti
    private Reparto reparto;

    @OneToMany(mappedBy = "barCodeId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Nullable
    @ToString.Exclude
    private List<BarCode> barCodeIds;

    @Column(name = "PREZZO")
    private Float prezzo;

    @Column(name = "STOCK")
    private Long stock;
}