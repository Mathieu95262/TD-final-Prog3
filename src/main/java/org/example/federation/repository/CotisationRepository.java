package org.example.federation.repository;


import org.example.federation.entity.Cotisation;
import org.example.federation.enums.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Long> {
    List<Cotisation> findByCollectiviteId(Long collectiviteId);
    List<Cotisation> findByCollectiviteIdAndTypeCotisation(Long collectiviteId, TypeCotisation type);
}