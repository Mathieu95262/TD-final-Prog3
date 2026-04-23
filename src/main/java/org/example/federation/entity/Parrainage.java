package org.example.federation.entity;

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

    // AJOUT : Le membre qui est parrainé (le filleul)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filleul_id")
    private Membre filleul;

    @Column(name = "relation", nullable = false)
    private String relation;

    // Lien temporaire pour les candidats qui ne sont pas encore membres
    @Column(name = "candidat_email")
    private String candidatEmail;
}