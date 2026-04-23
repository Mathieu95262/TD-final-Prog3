package org.example.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MOBILE_MONEY")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CompteMobileMoney extends Compte {

    // Services : Orange Money, Mvola, Airtel Money
    @Column(name = "service_mobile_money")
    private String serviceMobileMoney;

    // Numéro de téléphone unique associé au compte
    @Column(name = "numero_telephone", unique = true)
    private String numeroTelephone;
}