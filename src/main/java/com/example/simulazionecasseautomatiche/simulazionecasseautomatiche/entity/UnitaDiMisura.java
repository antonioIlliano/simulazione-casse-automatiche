package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UNITA_DI_MISURA_ENUM")
public class UnitaDiMisura {

    @Id
    @Column(name = "UDM_ID")
    private Long id;

    @Column(name = "UDM_NOME")
    private String nomeUdm;

    @Column(name = "UDM_CODE")
    private String codiceUdm;

    @OneToMany(mappedBy = "udm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Prodotto> prodotti;



}
