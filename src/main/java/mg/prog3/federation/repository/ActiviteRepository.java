package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Activite;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ActiviteRepository {

    private final Connection connection;

    public ActiviteRepository(Connection connection) {
        this.connection = connection;
    }

    public Activite save(Activite activite) {
        String sql = """
            INSERT INTO activites (titre, description, date_activite, type_activite, obligatoire, collectivite_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, activite.getTitre());
            ps.setString(2, activite.getDescription());
            ps.setDate(3, Date.valueOf(activite.getDateActivite()));
            ps.setString(4, activite.getTypeActivite());
            ps.setBoolean(5, activite.isObligatoire());
            ps.setLong(6, activite.getCollectiviteId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    activite.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'activité", e);
        }
        return activite;
    }

    public List<Activite> saveAll(List<Activite> activites) {
        String sql = """
            INSERT INTO activites (titre, description, date_activite, type_activite, obligatoire, collectivite_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Activite a : activites) {
                ps.setString(1, a.getTitre());
                ps.setString(2, a.getDescription());
                ps.setDate(3, Date.valueOf(a.getDateActivite()));
                ps.setString(4, a.getTypeActivite());
                ps.setBoolean(5, a.isObligatoire());
                ps.setLong(6, a.getCollectiviteId());
                ps.addBatch();
            }
            ps.executeBatch();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                int i = 0;
                while (rs.next() && i < activites.size()) {
                    activites.get(i).setId(rs.getLong(1));
                    i++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion des activités", e);
        }
        return activites;
    }

    public Optional<Activite> findById(Long id) {
        String sql = "SELECT * FROM activites WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche", e);
        }
        return Optional.empty();
    }

    public List<Activite> findByCollectiviteId(Long collectiviteId) {
        List<Activite> activites = new ArrayList<>();
        String sql = "SELECT * FROM activites WHERE collectivite_id = ? ORDER BY date_activite DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) activites.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche", e);
        }
        return activites;
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM activites WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification", e);
        }
        return false;
    }

    private Activite mapRow(ResultSet rs) throws SQLException {
        return Activite.builder()
                .id(rs.getLong("id"))
                .titre(rs.getString("titre"))
                .description(rs.getString("description"))
                .dateActivite(rs.getDate("date_activite").toLocalDate())
                .typeActivite(rs.getString("type_activite"))
                .obligatoire(rs.getBoolean("obligatoire"))
                .collectiviteId(rs.getLong("collectivite_id"))
                .build();
    }
}