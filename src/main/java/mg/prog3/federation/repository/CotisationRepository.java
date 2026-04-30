package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.enums.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Long> {
    Collection<Cotisation> findByCollectiviteId(Long collectiviteId);
    Collection<Cotisation> findByCollectiviteIdAndTypeCotisation(Long collectiviteId, TypeCotisation type);
}