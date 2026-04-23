package org.example.federation.dto.response;


import lombok.Builder;
import lombok.Data;
import org.example.federation.enums.TypeCompte;
import java.time.LocalDate;

@Data @Builder
public class CompteResponse {
    private Long id;
    private TypeCompte typeCompte;
    private String nomTitulaire;
    private Long solde;
    private LocalDate dateSolde;
    private Long collectiviteId;
    private boolean appartientFederation;

    private String nomBanque;
    private String numeroCompteBancaire;

    private String serviceMobileMoney;
    private String numeroTelephone;
}
