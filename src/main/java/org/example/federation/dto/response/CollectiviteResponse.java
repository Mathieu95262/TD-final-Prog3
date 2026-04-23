package org.example.federation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CollectiviteResponse {
    private Long id;
    private String numero;
    private String nom;
    private String ville;
    private String specialiteAgricole;
    private LocalDate dateCreation;
    private boolean autorisationOuverture;
    private Long cotisationAnnuelleObligatoire;
    private long nombreMembres;
}