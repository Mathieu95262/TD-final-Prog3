package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.enums.Genre;
import mg.prog3.federation.enums.Poste;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MembreRepository {

    private final Connection connection;

    public MembreRepository(Connection connection) {
        this.connection = connection;
    }

    public Membre save(Membre membre) {
        if (membre.getId() == null) {
            return insert(membre);
        } else {
            return update(membre);
        }
    }

    private Membre insert(Membre m) {
        String sql = """
            INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, 
                               telephone, email, date_adhesion, poste, actif, collectivite_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setMembreParams(ps, m);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du membre", e);
        }
        return m;
    }

    private Membre update(Membre m) {
        String sql = """
            UPDATE membres SET nom=?, prenom=?, date_naissance=?, genre=?, adresse=?, 
                             metier=?, telephone=?, email=?, date_adhesion=?, poste=?, 
                             actif=?, collectivite_id=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setMembreParams(ps, m);
            ps.setLong(13, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du membre", e);
        }
        return m;
    }

    public List<Membre> saveAll(List<Membre> membres) {
        String sql = """
            INSERT INTO membres (nom, prenom, date_naissance, genre, adresse, metier, 
                               telephone, email, date_adhesion, poste, actif, collectivite_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Membre m : membres) {
                setMembreParams(ps, m);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion des membres", e);
        }
        return membres;
    }

    public Optional<Membre> findById(Long id) {
        String sql = "SELECT * FROM membres WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du membre", e);
        }
        return Optional.empty();
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM membres WHERE id = ?";
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

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM membres WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'email", e);
        }
        return false;
    }

    public Optional<Membre> findByEmail(String email) {
        String sql = "SELECT * FROM membres WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par email", e);
        }
        return Optional.empty();
    }

    public List<Membre> findByCollectiviteId(Long collectiviteId) {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membres WHERE collectivite_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    membres.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par collectivité", e);
        }
        return membres;
    }

    public List<Membre> findByCollectiviteIdAndPoste(Long collectiviteId, Poste poste) {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM membres WHERE collectivite_id = ? AND poste = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            ps.setString(2, poste.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    membres.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par poste", e);
        }
        return membres;
    }

    public long countMembresAvecAnciennete(Long collectiviteId, LocalDate dateSeuil) {
        String sql = """
            SELECT COUNT(*) FROM membres 
            WHERE collectivite_id = ? AND date_adhesion <= ? AND actif = true
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            ps.setDate(2, Date.valueOf(dateSeuil));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des membres avec ancienneté", e);
        }
        return 0;
    }

    public long countMembresActifs(Long collectiviteId) {
        String sql = "SELECT COUNT(*) FROM membres WHERE collectivite_id = ? AND actif = true";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des membres actifs", e);
        }
        return 0;
    }

    public Optional<Membre> findMembreConfirmeAvecAnciennete(Long membreId, LocalDate dateSeuil) {
        String sql = """
            SELECT * FROM membres 
            WHERE id = ? AND poste = 'CONFIRMED_MEMBER' AND date_adhesion <= ? AND actif = true
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, membreId);
            ps.setDate(2, Date.valueOf(dateSeuil));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du membre confirmé", e);
        }
        return Optional.empty();
    }

    private void setMembreParams(PreparedStatement ps, Membre m) throws SQLException {
        ps.setString(1, m.getNom());
        ps.setString(2, m.getPrenom());
        ps.setDate(3, Date.valueOf(m.getDateNaissance()));
        ps.setString(4, m.getGenre().name());
        ps.setString(5, m.getAdresse());
        ps.setString(6, m.getMetier());
        ps.setString(7, m.getTelephone());
        ps.setString(8, m.getEmail());
        ps.setDate(9, Date.valueOf(m.getDateAdhesion()));
        ps.setString(10, m.getPoste().name());
        ps.setBoolean(11, m.isActif());
        ps.setLong(12, m.getCollectiviteId());
    }

    private Membre mapRow(ResultSet rs) throws SQLException {
        return Membre.builder()
                .id(rs.getLong("id"))
                .nom(rs.getString("nom"))
                .prenom(rs.getString("prenom"))
                .dateNaissance(rs.getDate("date_naissance").toLocalDate())
                .genre(Genre.valueOf(rs.getString("genre")))
                .adresse(rs.getString("adresse"))
                .metier(rs.getString("metier"))
                .telephone(rs.getString("telephone"))
                .email(rs.getString("email"))
                .dateAdhesion(rs.getDate("date_adhesion").toLocalDate())
                .poste(Poste.valueOf(rs.getString("poste")))
                .actif(rs.getBoolean("actif"))
                .collectiviteId(rs.getLong("collectivite_id"))
                .build();
    }
}