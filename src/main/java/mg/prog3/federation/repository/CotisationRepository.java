package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.enums.TypeCotisation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CotisationRepository {

    private final Connection connection;

    public CotisationRepository(Connection connection) {
        this.connection = connection;
    }

    public Cotisation save(Cotisation cotisation) {
        if (cotisation.getId() == null) {
            return insert(cotisation);
        } else {
            return update(cotisation);
        }
    }

    private Cotisation insert(Cotisation c) {
        String sql = """
            INSERT INTO cotisations (type_cotisation, montant, description, collectivite_id)
            VALUES (?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getTypeCotisation().name());
            ps.setLong(2, c.getMontant());
            ps.setString(3, c.getDescription());
            ps.setLong(4, c.getCollectiviteId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de la cotisation", e);
        }
        return c;
    }

    private Cotisation update(Cotisation c) {
        String sql = """
            UPDATE cotisations SET type_cotisation=?, montant=?, description=?, collectivite_id=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getTypeCotisation().name());
            ps.setLong(2, c.getMontant());
            ps.setString(3, c.getDescription());
            ps.setLong(4, c.getCollectiviteId());
            ps.setLong(5, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la cotisation", e);
        }
        return c;
    }

    public Optional<Cotisation> findById(Long id) {
        String sql = "SELECT * FROM cotisations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la cotisation", e);
        }
        return Optional.empty();
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM cotisations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de la cotisation", e);
        }
        return false;
    }

    public List<Cotisation> findByCollectiviteId(Long collectiviteId) {
        List<Cotisation> cotisations = new ArrayList<>();
        String sql = "SELECT * FROM cotisations WHERE collectivite_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cotisations.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des cotisations", e);
        }
        return cotisations;
    }

    private Cotisation mapRow(ResultSet rs) throws SQLException {
        return Cotisation.builder()
                .id(rs.getLong("id"))
                .typeCotisation(TypeCotisation.valueOf(rs.getString("type_cotisation")))
                .montant(rs.getLong("montant"))
                .description(rs.getString("description"))
                .collectiviteId(rs.getLong("collectivite_id"))
                .build();
    }
}