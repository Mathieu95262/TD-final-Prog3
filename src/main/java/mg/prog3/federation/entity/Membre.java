package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.prog3.federation.enums.Genre;
import mg.prog3.federation.enums.Poste;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membre {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Genre genre;
    private String adresse;
    private String metier;
    private String telephone;
    private String email;
    private LocalDate dateAdhesion;
    private Poste poste;
    private boolean actif;
    private Long collectiviteId;
}