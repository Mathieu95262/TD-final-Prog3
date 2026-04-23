package org.example.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CAISSE")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Caisse extends Compte {
    // Pas de champs supplémentaires : la caisse contient uniquement les espèces
}