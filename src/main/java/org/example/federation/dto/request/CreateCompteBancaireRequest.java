package org.example.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCompteBancaireRequest {

    @NotBlank(message = "Le nom du titulaire est obligatoire")
    private String nomTitulaire;

    @NotBlank(message = "Le nom de la banque est obligatoire")
    // BRED, MCB, BMOI, BOA, BGFI, AFG, ACCES_BANQUE, BAOBAB, SIPEM
    private String nomBanque;

    // Format: BBBBBGGGGGCCCCCCCCCCCКК (23 chiffres)
    @NotBlank(message = "Le numéro de compte est obligatoire")
    @Pattern(regexp = "^\\d{23}$", message = "Le numéro de compte doit contenir exactement 23 chiffres")
    private String numeroCompteBancaire;

    @NotNull(message = "Le solde initial est obligatoire")
    private Long soldeInitial;

    // null = appartient à une collectivité, true = appartient à la fédération
    private Long collectiviteId;
    private Boolean appartientFederation;
}