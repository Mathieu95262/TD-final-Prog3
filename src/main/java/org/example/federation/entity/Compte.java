package org.example.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.federation.enums.TypeCompte;

import java.time.LocalDate;

@Entity
@Table(name = "comptes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_compte", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_titulaire", nullable = false)
    private String nomTitulaire;

    @Column(name = "solde", nullable = false)
    private Long solde; // en MGA

    @Column(name = "date_solde", nullable = false)
    private LocalDate dateSolde;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_compte", insertable = false, updatable = false)
    private TypeCompte typeCompte;

    // Un compte appartient soit à une collectivité, soit à la fédération
    // Pour simplifier : nullable, selon qu'il appartient à collectivité ou fédération
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id")
    private Collectivite collectivite;

    // true si appartient à la fédération (et non à une collectivité)
    @Column(name = "appartient_federation")
    private boolean appartientFederation;
}