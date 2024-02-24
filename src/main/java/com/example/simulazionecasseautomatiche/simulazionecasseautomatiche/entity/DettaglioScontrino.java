package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "DETTAGLIO_SCONTRINO")
public class DettaglioScontrino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long prodottoId;

    private Float prezzo;

    private Long quantita;

    @ManyToOne
    @ToString.Exclude
    private Scontrino scontrinoId;


}
