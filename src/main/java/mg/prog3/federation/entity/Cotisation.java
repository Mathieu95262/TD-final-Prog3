package mg.prog3.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import mg.prog3.federation.enums.TypeCotisation;

import java.util.List;

@Entity
@Table(name = "cotisations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_cotisation", nullable = false)
    private TypeCotisation typeCotisation;

    @Column(name = "montant", nullable = false)
    private Long montant;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectivite_id", nullable = false)
    private Collectivite collectivite;

    @OneToMany(mappedBy = "cotisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Paiement> paiements;
}
