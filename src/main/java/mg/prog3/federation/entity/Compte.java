package mg.prog3.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mg.prog3.federation.enums.TypeCompte;

import java.time.LocalDate;

@Entity
@Table(name = "comptes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
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
    private Long solde;

    @Column(name = "date_solde", nullable = false)
    private LocalDate dateSolde;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_compte", nullable = false)
    private TypeCompte typeCompte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id")
    private Collectivite collectivite;

    @Column(name = "appartient_federation", nullable = false)
    private boolean appartientFederation;
}