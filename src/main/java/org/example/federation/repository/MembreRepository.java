package org.example.federation.repository;

import org.example.federation.entity.Membre;
import org.example.federation.enums.Poste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    boolean existsByEmail(String email);

    Optional<Membre> findByEmail(String email);

    List<Membre> findByCollectiviteId(Long collectiviteId);

    // Ajouté pour la vérification d'unicité (Président/Trésorier)
    boolean existsByCollectiviteIdAndPoste(Long collectiviteId, Poste poste);

    List<Membre> findByCollectiviteIdAndPoste(Long collectiviteId, Poste poste);

    @Query("SELECT COUNT(m) FROM Membre m WHERE m.collectivite.id = :collectiviteId AND m.dateAdhesion <= :dateSeuil AND m.actif = true")
    long countMembresAvecAnciennete(@Param("collectiviteId") Long collectiviteId, @Param("dateSeuil") LocalDate dateSeuil);

    @Query("SELECT COUNT(m) FROM Membre m WHERE m.collectivite.id = :collectiviteId AND m.actif = true")
    long countMembresActifs(@Param("collectiviteId") Long collectiviteId);

    @Query("SELECT m FROM Membre m WHERE m.id = :membreId AND m.poste = 'MEMBRE_CONFIRME' AND m.dateAdhesion <= :dateSeuil AND m.actif = true")
    Optional<Membre> findMembreConfirmeAvecAnciennete(@Param("membreId") Long membreId, @Param("dateSeuil") LocalDate dateSeuil);
}