package org.example.federation.repository;


import org.example.federation.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByMembreId(Long membreId);

    List<Paiement> findByCotisationId(Long cotisationId);

    // Paiements d'un membre sur une période donnée
    @Query("SELECT p FROM Paiement p WHERE p.membre.id = :membreId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    List<Paiement> findByMembreIdAndPeriode(
            @Param("membreId") Long membreId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // Somme des paiements d'un membre sur une période
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.membre.id = :membreId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    Long sumMontantByMembreIdAndPeriode(
            @Param("membreId") Long membreId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    // Tous les paiements d'une collectivité (via cotisation)
    @Query("SELECT p FROM Paiement p WHERE p.cotisation.collectivite.id = :collectiviteId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    List<Paiement> findByCollectiviteIdAndPeriode(
            @Param("collectiviteId") Long collectiviteId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);
}