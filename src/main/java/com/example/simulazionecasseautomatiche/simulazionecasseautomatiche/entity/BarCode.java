package com.example.simulazionecasseautomatiche.simulazionecasseautomatiche.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BAR_CODE")
@Builder
public class BarCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BAR_CODE_ID")
    private Long barCodeId;

    private String startDate;

    private String endDate;

    @Column(name = "BAR_CODE_CODE")
    private String code;

    @JoinColumn(name = "productId")
    @ManyToOne
    private Prodotto productId;


}
