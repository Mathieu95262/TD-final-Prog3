package org.example.federation.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompteMobileMoneyRequest {

    @NotBlank(message = "Le nom du titulaire est obligatoire")
    private String nomTitulaire;

    // Orange Money, Mvola, Airtel Money
    @NotBlank(message = "Le service mobile money est obligatoire")
    private String serviceMobileMoney;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String numeroTelephone;

    @NotNull(message = "Le solde initial est obligatoire")
    private Long soldeInitial;

    private Long collectiviteId;
    private Boolean appartientFederation;
}