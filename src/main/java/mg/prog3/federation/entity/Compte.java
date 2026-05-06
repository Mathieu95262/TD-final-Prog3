package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.prog3.federation.enums.TypeCompte;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compte {
    private Long id;
    private String nomTitulaire;
    private Long solde;
    private LocalDate dateSolde;
    private TypeCompte typeCompte;
    private Long collectiviteId;
    private boolean appartientFederation;
    private String nomBanque;
    private String numeroCompteBancaire;
    private String serviceMobileMoney;
    private String numeroTelephone;
}