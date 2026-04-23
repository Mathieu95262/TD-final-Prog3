package org.example.federation.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.federation.enums.Genre;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateMembreRequest {

    @NotNull(message = "L'identifiant de la collectivité est obligatoire")
    private Long collectiviteId;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    @NotNull(message = "Le genre est obligatoire")
    private Genre genre;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "Le métier est obligatoire")
    private String metier;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    // B-2: at least 2 confirmed sponsors
    @NotNull(message = "La liste des parrains est obligatoire")
    @Size(min = 2, message = "Au moins 2 parrains confirmés sont requis")
    @Valid
    private List<ParrainRequest> parrains;

    // Payment info
    @NotNull(message = "Le montant payé est obligatoire")
    @Positive(message = "Le montant payé doit être positif")
    private Long montantPaye;

    @NotBlank(message = "Le mode de paiement est obligatoire")
    private String modePaiement; // ESPECE, VIREMENT_BANCAIRE, MOBILE_MONEY
}