package mg.prog3.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import mg.prog3.federation.enums.Genre;
import mg.prog3.federation.enums.Poste;

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

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "metier", nullable = false)
    private String metier;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "date_adhesion", nullable = false)
    private LocalDate dateAdhesion;

    @Enumerated(EnumType.STRING)
    @Column(name = "poste", nullable = false)
    private Poste poste;

    @Column(name = "actif", nullable = false)
    @Builder.Default
    private boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id", nullable = false)
    private Collectivite collectivite;
}