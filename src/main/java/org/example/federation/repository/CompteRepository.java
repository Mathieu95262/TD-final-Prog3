package org.example.federation.repository;

import org.example.federation.entity.Compte;
import org.example.federation.enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    List<Compte> findByCollectiviteId(Long collectiviteId);

    List<Compte> findByCollectiviteIdAndTypeCompte(Long collectiviteId, TypeCompte typeCompte);

    List<Compte> findByAppartientFederationTrue();

    List<Compte> findByAppartientFederationTrueAndTypeCompte(TypeCompte typeCompte);

    boolean existsByCollectiviteIdAndTypeCompte(Long collectiviteId, TypeCompte typeCompte);

    boolean existsByAppartientFederationTrueAndTypeCompte(TypeCompte typeCompte);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CompteBancaire c WHERE c.numeroCompteBancaire = :numero")
    boolean existsByNumeroCompteBancaire(@Param("numero") String numero);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CompteMobileMoney c WHERE c.numeroTelephone = :telephone")
    boolean existsByNumeroTelephone(@Param("telephone") String telephone);
}