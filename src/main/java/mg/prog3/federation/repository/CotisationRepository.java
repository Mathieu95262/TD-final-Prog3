package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.enums.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Long> {
    List<Cotisation> findByCollectiviteId(Long collectiviteId);
    List<Cotisation> findByCollectiviteIdAndTypeCotisation(Long collectiviteId, TypeCotisation type);
}
