package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Collectivite;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CollectiviteRepository {

    private final Connection connection;

    public CollectiviteRepository(Connection connection) {
        this.connection = connection;
    }

    public Collectivite save(Collectivite collectivite) {
        if (collectivite.getId() == null) {
            return insert(collectivite);
        } else {
            return update(collectivite);
        }
    }

    private Collectivite insert(Collectivite c) {
        String sql = """
            INSERT INTO collectivities (numero, nom, ville, specialite_agricole, date_creation, 
                                       autorisation_ouverture, cotisation_annuelle_obligatoire)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNumero());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getVille());
            ps.setString(4, c.getSpecialiteAgricole());
            ps.setDate(5, Date.valueOf(c.getDateCreation()));
            ps.setBoolean(6, c.isAutorisationOuverture());
            if (c.getCotisationAnnuelleObligatoire() != null) {
                ps.setLong(7, c.getCotisationAnnuelleObligatoire());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de la collectivité", e);
        }
        return c;
    }

    private Collectivite update(Collectivite c) {
        String sql = """
            UPDATE collectivities SET numero=?, nom=?, ville=?, specialite_agricole=?, 
                                     date_creation=?, autorisation_ouverture=?, cotisation_annuelle_obligatoire=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getNumero());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getVille());
            ps.setString(4, c.getSpecialiteAgricole());
            ps.setDate(5, Date.valueOf(c.getDateCreation()));
            ps.setBoolean(6, c.isAutorisationOuverture());
            if (c.getCotisationAnnuelleObligatoire() != null) {
                ps.setLong(7, c.getCotisationAnnuelleObligatoire());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            ps.setLong(8, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la collectivité", e);
        }
        return c;
    }

    public Optional<Collectivite> findById(Long id) {
        String sql = "SELECT * FROM collectivities WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la collectivité", e);
        }
        return Optional.empty();
    }

    public List<Collectivite> findAll() {
        List<Collectivite> collectivites = new ArrayList<>();
        String sql = "SELECT * FROM collectivities";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                collectivites.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des collectivités", e);
        }
        return collectivites;
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM collectivities WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence", e);
        }
        return false;
    }

    public boolean existsByNom(String nom) {
        String sql = "SELECT COUNT(*) FROM collectivities WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du nom", e);
        }
        return false;
    }

    public boolean existsByNumero(String numero) {
        String sql = "SELECT COUNT(*) FROM collectivities WHERE numero = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du numéro", e);
        }
        return false;
    }

    private Collectivite mapRow(ResultSet rs) throws SQLException {
        Long cotisation = rs.getLong("cotisation_annuelle_obligatoire");
        if (rs.wasNull()) cotisation = null;

        return Collectivite.builder()
                .id(rs.getLong("id"))
                .numero(rs.getString("numero"))
                .nom(rs.getString("nom"))
                .ville(rs.getString("ville"))
                .specialiteAgricole(rs.getString("specialite_agricole"))
                .dateCreation(rs.getDate("date_creation").toLocalDate())
                .autorisationOuverture(rs.getBoolean("autorisation_ouverture"))
                .cotisationAnnuelleObligatoire(cotisation)
                .build();
    }
}