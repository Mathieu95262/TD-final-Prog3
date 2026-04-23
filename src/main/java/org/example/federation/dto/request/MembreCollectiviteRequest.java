package org.example.federation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.federation.enums.Genre;
import org.example.federation.enums.Poste;

import java.time.LocalDate;

@Data
public class MembreCollectiviteRequest {

    // --- Identifiants pour l'assignation ---
    private Long membreId;
    private Long collectiviteId;

    // --- Informations personnelles (utilisées lors de la création de la collectivité) ---
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

    // --- Poste au sein de la collectivité ---
    @NotNull(message = "Le poste est obligatoire")
    private Poste poste;

    // --- Date d'adhésion pour le calcul de l'ancienneté (Règle des 6 mois) ---
    private LocalDate dateAdhesionFederation;
}