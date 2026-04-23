package org.example.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.federation.enums.Genre;
import org.example.federation.enums.Poste;

import java.time.LocalDate;

@Entity
@Table(name = "membres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String metier;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateAdhesion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Poste poste;

    @Column(nullable = false)
    private boolean actif = true;

    // --- Relation avec la Collectivité ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id") // Enlevé nullable=false car un membre peut être créé sans collectivité au début
    private Collectivite collectivite;

    // --- Informations d'Identité (CIN) ---
    private String cin;
    private LocalDate dateDelivranceCin;
    private String lieuDelivranceCin;
}