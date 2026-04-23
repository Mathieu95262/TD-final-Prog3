package org.example.federation.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("BANCAIRE")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CompteBancaire extends Compte {

    // Banques disponibles : BRED, MCB, BMOI, BOA, BGFI, AFG, ACCES_BANQUE, BAOBAB, SIPEM
    @Column(name = "nom_banque")
    private String nomBanque;

    // Format : BBBBBGGGGGCCCCCCCCCCCCKK (23 chiffres)
    // BBBBB=code banque, GGGGG=code guichet, CCCCCCCCCCC=numéro compte, KK=clé RIB
    @Column(name = "numero_compte_bancaire", unique = true, length = 23)
    private String numeroCompteBancaire;
}