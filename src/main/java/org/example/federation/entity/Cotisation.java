package org.example.federation.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.federation.enums.TypeCotisation;

import java.util.List;

@Entity
@Table(name = "cotisations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_cotisation", nullable = false)
    private TypeCotisation typeCotisation;

    @Column(name = "montant", nullable = false)
    private Long montant; // en MGA

    @Column(name = "description")
    private String description;

    // Cotisation associée à une collectivité spécifique
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id", nullable = false)
    private Collectivite collectivite;

    // Liste de paiements effectués pour cette cotisation
    @OneToMany(mappedBy = "cotisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements;
}