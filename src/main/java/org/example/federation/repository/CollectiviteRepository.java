package org.example.federation.repository;


import org.example.federation.entity.Collectivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectiviteRepository extends JpaRepository<Collectivite, Long> {
    boolean existsByNom(String nom);
    boolean existsByNumero(String numero);
    Optional<Collectivite> findByNom(String nom);
    Optional<Collectivite> findByNumero(String numero);
}