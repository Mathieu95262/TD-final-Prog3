package org.example.federation.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.federation.enums.ModePaiement;

import java.time.LocalDate;

@Data
public class CreatePaiementRequest {

    @NotNull(message = "L'identifiant du membre est obligatoire")
    private Long membreId;

    @NotNull(message = "L'identifiant de la cotisation est obligatoire")
    private Long cotisationId;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Long montant;

    @NotNull(message = "La date d'encaissement est obligatoire")
    private LocalDate dateEncaissement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;
}
