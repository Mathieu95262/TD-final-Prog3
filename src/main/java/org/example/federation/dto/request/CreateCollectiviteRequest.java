package org.example.federation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateCollectiviteRequest {

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "La spécialité agricole est obligatoire")
    private String specialiteAgricole;

    @NotNull(message = "La liste des membres est obligatoire")
    @Size(min = 10, message = "Une collectivité doit avoir au moins 10 membres")
    @Valid
    private List<MembreCollectiviteRequest> membres;

    @NotNull(message = "Le montant de cotisation annuelle est obligatoire")
    private Long cotisationAnnuelleObligatoire;
}