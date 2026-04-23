package org.example.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AssignIdentityRequest {

    //MEMBRE
    private String cin;
    private LocalDate dateDelivrance;
    private String lieuDelivrance;

    //COLLECTIVITÉ
    @NotBlank(message = "Le numéro est obligatoire pour une collectivité")
    private String numero;

    @NotBlank(message = "Le nom est obligatoire pour une collectivité")
    private String nom;
}