package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;
import mg.prog3.federation.enums.Genre;
import mg.prog3.federation.enums.Poste;

import java.time.LocalDate;

@Data
@Builder
public class MembreResponse {
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
    private String collectiviteNom;
}
