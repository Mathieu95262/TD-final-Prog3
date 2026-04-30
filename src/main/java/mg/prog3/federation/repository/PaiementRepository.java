package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Collection<Paiement> findByMembreId(Long membreId);

    Collection<Paiement> findByCotisationId(Long cotisationId);

    @Query("SELECT p FROM Paiement p WHERE p.membre.id = :membreId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    Collection<Paiement> findByMembreIdAndPeriode(
            @Param("membreId") Long membreId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.membre.id = :membreId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    Long sumMontantByMembreIdAndPeriode(
            @Param("membreId") Long membreId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);

    @Query("SELECT p FROM Paiement p WHERE p.cotisation.collectivite.id = :collectiviteId " +
            "AND p.dateEncaissement BETWEEN :debut AND :fin")
    Collection<Paiement> findByCollectiviteIdAndPeriode(
            @Param("collectiviteId") Long collectiviteId,
            @Param("debut") LocalDate debut,
            @Param("fin") LocalDate fin);
}