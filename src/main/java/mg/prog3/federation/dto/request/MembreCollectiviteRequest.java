package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.prog3.federation.enums.Genre;
import mg.prog3.federation.enums.Poste;

import java.time.LocalDate;

@Data
public class MembreCollectiviteRequest {

    @NotBlank(message = "Last name is required")
    private String nom;

    @NotBlank(message = "First name is required")
    private String prenom;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateNaissance;

    @NotNull(message = "Gender is required")
    private Genre genre;

    @NotBlank(message = "Address is required")
    private String adresse;

    @NotBlank(message = "Occupation is required")
    private String metier;

    @NotBlank(message = "Phone number is required")
    private String telephone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Position is required")
    private Poste poste;

    private Long membreId;

    private LocalDate dateAdhesionFederation;
}
