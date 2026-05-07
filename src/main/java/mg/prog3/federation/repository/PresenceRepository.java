package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Presence;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresenceRepository {

    private final Connection connection;

    public PresenceRepository(Connection connection) {
        this.connection = connection;
    }

    public Presence save(Presence presence) {
        String sql = """
            INSERT INTO presences (activite_id, membre_id, present, excuse, motif)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (activite_id, membre_id) DO NOTHING
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, presence.getActiviteId());
            ps.setLong(2, presence.getMembreId());
            ps.setBoolean(3, presence.isPresent());
            ps.setBoolean(4, presence.isExcuse());
            ps.setString(5, presence.getMotif());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) presence.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de la présence", e);
        }
        return presence;
    }

    public List<Presence> saveAll(List<Presence> presences) {
        String sql = """
            INSERT INTO presences (activite_id, membre_id, present, excuse, motif)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT (activite_id, membre_id) DO NOTHING
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Presence p : presences) {
                ps.setLong(1, p.getActiviteId());
                ps.setLong(2, p.getMembreId());
                ps.setBoolean(3, p.isPresent());
                ps.setBoolean(4, p.isExcuse());
                ps.setString(5, p.getMotif());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion des présences", e);
        }
        return presences;
    }

    public List<Presence> findByActiviteId(Long activiteId) {
        List<Presence> presences = new ArrayList<>();
        String sql = "SELECT * FROM presences WHERE activite_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, activiteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) presences.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche", e);
        }
        return presences;
    }

    public List<Presence> findByMembreIdAndPeriode(Long membreId, Date debut, Date fin) {
        List<Presence> presences = new ArrayList<>();
        String sql = """
            SELECT p.* FROM presences p
            JOIN activites a ON p.activite_id = a.id
            WHERE p.membre_id = ? AND a.date_activite BETWEEN ? AND ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, membreId);
            ps.setDate(2, debut);
            ps.setDate(3, fin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) presences.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche", e);
        }
        return presences;
    }

    private Presence mapRow(ResultSet rs) throws SQLException {
        return Presence.builder()
                .id(rs.getLong("id"))
                .activiteId(rs.getLong("activite_id"))
                .membreId(rs.getLong("membre_id"))
                .present(rs.getBoolean("present"))
                .excuse(rs.getBoolean("excuse"))
                .motif(rs.getString("motif"))
                .build();
    }
    public long countPresencesByMembreIdAndPeriode(Long membreId, Date debut, Date fin) {
        String sql = """
        SELECT COUNT(*) FROM presences p
        JOIN activites a ON p.activite_id = a.id
        WHERE p.membre_id = ? AND a.date_activite BETWEEN ? AND ? AND p.present = true
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, membreId);
            ps.setDate(2, debut);
            ps.setDate(3, fin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur comptage présences", e);
        }
        return 0;
    }

    public long countTotalActivitesByCollectiviteAndPeriode(Long collectiviteId, Date debut, Date fin) {
        String sql = """
        SELECT COUNT(*) FROM activites
        WHERE collectivite_id = ? AND date_activite BETWEEN ? AND ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            ps.setDate(2, debut);
            ps.setDate(3, fin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur comptage activités", e);
        }
        return 0;
    }
}