package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SCONTRINO")
public class Scontrino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scontrinoId;

    private Float totaleScontrino;

    private LocalDate dataDiAcquisto;

    private String codiceScontrino;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "scontrinoId")
    @ToString.Exclude
    private List<DettaglioScontrino> dettaglioScontrino;

    private Boolean contabilizzato;
}
