package mg.prog3.federation.entity;

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

    @Column(name = "service_mobile_money")
    private String serviceMobileMoney;

    @Column(name = "numero_telephone", unique = true)
    private String numeroTelephone;
}
