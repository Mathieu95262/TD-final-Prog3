package mg.prog3.federation.entity;

import jakarta.persistence.*;
import lombok.*;
import mg.prog3.federation.enums.ModePaiement;

import java.time.LocalDate;

@Entity
@Table(name = "paiements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "montant", nullable = false)
    private Long montant;

    @Column(name = "date_encaissement", nullable = false)
    private LocalDate dateEncaissement;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", nullable = false)
    private ModePaiement modePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cotisation_id", nullable = false)
    private Cotisation cotisation;
}