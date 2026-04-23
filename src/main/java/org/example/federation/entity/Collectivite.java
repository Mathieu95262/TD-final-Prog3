package org.example.federation.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "collectivites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collectivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", unique = true)
    private String numero;

    @Column(name = "nom", unique = true)
    private String nom;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "specialite_agricole", nullable = false)
    private String specialiteAgricole;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "autorisation_ouverture", nullable = false)
    private boolean autorisationOuverture;

    @Column(name = "cotisation_annuelle_obligatoire")
    private Long cotisationAnnuelleObligatoire;

    @OneToMany(mappedBy = "collectivite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Membre> membres;
}