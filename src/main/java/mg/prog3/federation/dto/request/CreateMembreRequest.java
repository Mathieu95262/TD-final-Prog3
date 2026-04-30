package mg.prog3.federation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import mg.prog3.federation.enums.Genre;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class CreateMembreRequest {

    @NotNull(message = "Collectivite ID is required")
    private Long collectiviteId;

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

    @NotNull(message = "Sponsor list is required")
    @Size(min = 2, message = "At least 2 confirmed sponsors are required")
    @Valid
    private Collection<ParrainRequest> parrains;

    @NotNull(message = "Amount paid is required")
    @Positive(message = "Amount paid must be positive")
    private Long montantPaye;

    @NotBlank(message = "Payment method is required")
    private String modePaiement;
}