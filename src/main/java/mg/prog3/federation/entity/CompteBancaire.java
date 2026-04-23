package mg.prog3.federation.entity;

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

    @Column(name = "nom_banque")
    private String nomBanque;

    @Column(name = "numero_compte_bancaire", unique = true, length = 23)
    private String numeroCompteBancaire;
}
