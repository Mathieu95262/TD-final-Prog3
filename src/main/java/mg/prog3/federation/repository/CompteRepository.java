package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Compte;
import mg.prog3.federation.enums.TypeCompte;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompteRepository {

    private final Connection connection;

    public CompteRepository(Connection                                                                                                                                                                                       connection) {
        this.connection = connection;
    }

    public Compte save(Compte compte) {
        if (compte.getId() == null) {
            return insert(compte);
        } else {
            return update(compte);
        }
    }

    private Compte insert(Compte c) {
        String sql = """
            INSERT INTO comptes (dtype, nom_titulaire, solde, date_solde, type_compte, 
                                collectivite_id, appartient_federation, nom_banque, 
                                numero_compte_bancaire, service_mobile_money, numero_telephone)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCompteParams(ps, c);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du compte", e);
        }
        return c;
    }

    private Compte update(Compte c) {
        String sql = """
            UPDATE comptes SET nom_titulaire=?, solde=?, date_solde=?, type_compte=?,
                              collectivite_id=?, appartient_federation=?, nom_banque=?,
                              numero_compte_bancaire=?, service_mobile_money=?, numero_telephone=?
            WHERE id=?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCompteParams(ps, c);
            ps.setLong(11, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du compte", e);
        }
        return c;
    }

    public Optional<Compte> findById(Long id) {
        String sql = "SELECT * FROM comptes WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du compte", e);
        }
        return Optional.empty();
    }

    public List<Compte> findByCollectiviteId(Long collectiviteId) {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM comptes WHERE collectivite_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comptes.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des comptes", e);
        }
        return comptes;
    }

    public List<Compte> findByAppartientFederationTrue() {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM comptes WHERE appartient_federation = true";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comptes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des comptes fédéraux", e);
        }
        return comptes;
    }

    public boolean existsByCollectiviteIdAndTypeCompte(Long collectiviteId, TypeCompte typeCompte) {
        String sql = "SELECT COUNT(*) FROM comptes WHERE collectivite_id = ? AND type_compte = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            ps.setString(2, typeCompte.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du type de compte", e);
        }
        return false;
    }

    public boolean existsByAppartientFederationTrueAndTypeCompte(TypeCompte typeCompte) {
        String sql = "SELECT COUNT(*) FROM comptes WHERE appartient_federation = true AND type_compte = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, typeCompte.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du compte fédéral", e);
        }
        return false;
    }

    public boolean existsByNumeroCompteBancaire(String rib) {
        String sql = "SELECT COUNT(*) FROM comptes WHERE numero_compte_bancaire = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rib);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du RIB", e);
        }
        return false;
    }

    public boolean existsByNumeroTelephone(String telephone) {
        String sql = "SELECT COUNT(*) FROM comptes WHERE numero_telephone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du téléphone", e);
        }
        return false;
    }

    private void setCompteParams(PreparedStatement ps, Compte c) throws SQLException {
        // dtype
        switch (c.getTypeCompte()) {
            case CASH_REGISTER -> ps.setString(1, "CAISSE");
            case BANK -> ps.setString(1, "BANCAIRE");
            case MOBILE_MONEY -> ps.setString(1, "MOBILE_MONEY");
        }
        ps.setString(2, c.getNomTitulaire());
        ps.setLong(3, c.getSolde() != null ? c.getSolde() : 0L);
        ps.setDate(4, Date.valueOf(c.getDateSolde() != null ? c.getDateSolde() : LocalDate.now()));
        ps.setString(5, c.getTypeCompte().name());

        if (c.getCollectiviteId() != null) {
            ps.setLong(6, c.getCollectiviteId());
        } else {
            ps.setNull(6, Types.BIGINT);
        }

        ps.setBoolean(7, c.isAppartientFederation());
        ps.setString(8, c.getNomBanque());
        ps.setString(9, c.getNumeroCompteBancaire());
        ps.setString(10, c.getServiceMobileMoney());
        ps.setString(11, c.getNumeroTelephone());
    }

    private Compte mapRow(ResultSet rs) throws SQLException {
        Long collectiviteId = rs.getLong("collectivite_id");
        if (rs.wasNull()) collectiviteId = null;

        return Compte.builder()
                .id(rs.getLong("id"))
                .nomTitulaire(rs.getString("nom_titulaire"))
                .solde(rs.getLong("solde"))
                .dateSolde(rs.getDate("date_solde").toLocalDate())
                .typeCompte(TypeCompte.valueOf(rs.getString("type_compte")))
                .collectiviteId(collectiviteId)
                .appartientFederation(rs.getBoolean("appartient_federation"))
                .nomBanque(rs.getString("nom_banque"))
                .numeroCompteBancaire(rs.getString("numero_compte_bancaire"))
                .serviceMobileMoney(rs.getString("service_mobile_money"))
                .numeroTelephone(rs.getString("numero_telephone"))
                .build();
    }
}