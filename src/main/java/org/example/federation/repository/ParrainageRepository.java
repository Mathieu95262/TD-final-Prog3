package org.example.federation.repository;

import org.example.federation.entity.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParrainageRepository extends JpaRepository<Parrainage, Long> {
    // Aucune méthode personnalisée n'est nécessaire pour l'instant
}