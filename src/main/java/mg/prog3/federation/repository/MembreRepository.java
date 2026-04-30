package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.enums.Poste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    boolean existsByEmail(String email);

    Optional<Membre> findByEmail(String email);

    Collection<Membre> findByCollectiviteId(Long collectiviteId);

    Collection<Membre> findByCollectiviteIdAndPoste(Long collectiviteId, Poste poste);

    @Query("SELECT COUNT(m) FROM Membre m WHERE m.collectivite.id = :collectiviteId AND m.dateAdhesion <= :dateSeuil AND m.actif = true")
    long countMembresAvecAnciennete(@Param("collectiviteId") Long collectiviteId, @Param("dateSeuil") LocalDate dateSeuil);

    @Query("SELECT COUNT(m) FROM Membre m WHERE m.collectivite.id = :collectiviteId AND m.actif = true")
    long countMembresActifs(@Param("collectiviteId") Long collectiviteId);

    @Query("SELECT m FROM Membre m WHERE m.id = :membreId AND m.poste = 'CONFIRMED_MEMBER' AND m.dateAdhesion <= :dateSeuil AND m.actif = true")
    Optional<Membre> findMembreConfirmeAvecAnciennete(@Param("membreId") Long membreId, @Param("dateSeuil") LocalDate dateSeuil);
}