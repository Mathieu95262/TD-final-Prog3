package org.example.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.federation.enums.ModePaiement;

import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant", nullable = false)
    private Long montant; // en MGA

    @Column(name = "date_encaissement", nullable = false)
    private LocalDate dateEncaissement;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", nullable = false)
    private ModePaiement modePaiement;

    // Membre qui a payé
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    // Cotisation concernée
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotisation_id", nullable = false)
    private Cotisation cotisation;
}