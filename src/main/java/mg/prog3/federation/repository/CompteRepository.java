package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Compte;
import mg.prog3.federation.enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Collection<Compte> findByCollectiviteId(Long collectiviteId);

    Collection<Compte> findByCollectiviteIdAndTypeCompte(Long collectiviteId, TypeCompte typeCompte);

    Collection<Compte> findByAppartientFederationTrue();

    boolean existsByCollectiviteIdAndTypeCompte(Long collectiviteId, TypeCompte typeCompte);

    boolean existsByAppartientFederationTrueAndTypeCompte(TypeCompte typeCompte);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CompteBancaire c WHERE c.numeroCompteBancaire = :rib")
    boolean existsByNumeroCompteBancaire(@Param("rib") String rib);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CompteMobileMoney c WHERE c.numeroTelephone = :tel")
    boolean existsByNumeroTelephone(@Param("tel") String telephone);
}