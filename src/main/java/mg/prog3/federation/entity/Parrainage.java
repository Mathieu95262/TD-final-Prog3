package mg.prog3.federation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parrainages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parrainage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parrain_id", nullable = false)
    private Membre parrain;

    @Column(name = "relation", nullable = false)
    private String relation;

    @Column(name = "candidat_email")
    private String candidatEmail;
}