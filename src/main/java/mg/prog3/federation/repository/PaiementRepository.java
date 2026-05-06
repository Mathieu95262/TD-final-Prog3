package mg.prog3.federation.repository;

import mg.prog3.federation.entity.Paiement;
import mg.prog3.federation.enums.ModePaiement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaiementRepository {

    private final Connection connection;

    public PaiementRepository(Connection connection) {
        this.connection = connection;
    }

    public Paiement save(Paiement paiement) {
        String sql = """
            INSERT INTO paiements (montant, date_encaissement, mode_paiement, membre_id, cotisation_id)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, paiement.getMontant());
            ps.setDate(2, Date.valueOf(paiement.getDateEncaissement()));
            ps.setString(3, paiement.getModePaiement().name());
            ps.setLong(4, paiement.getMembreId());
            ps.setLong(5, paiement.getCotisationId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paiement.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du paiement", e);
        }
        return paiement;
    }

    public List<Paiement> findByMembreId(Long membreId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements WHERE membre_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, membreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des paiements", e);
        }
        return paiements;
    }

    public List<Paiement> findByCotisationId(Long cotisationId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements WHERE cotisation_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, cotisationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des paiements", e);
        }
        return paiements;
    }

    public List<Paiement> findByCollectiviteIdAndPeriode(Long collectiviteId, LocalDate debut, LocalDate fin) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = """
            SELECT p.* FROM paiements p
            JOIN cotisations c ON p.cotisation_id = c.id
            WHERE c.collectivite_id = ? AND p.date_encaissement BETWEEN ? AND ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, collectiviteId);
            ps.setDate(2, Date.valueOf(debut));
            ps.setDate(3, Date.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par période", e);
        }
        return paiements;
    }

    private Paiement mapRow(ResultSet rs) throws SQLException {
        return Paiement.builder()
                .id(rs.getLong("id"))
                .montant(rs.getLong("montant"))
                .dateEncaissement(rs.getDate("date_encaissement").toLocalDate())
                .modePaiement(ModePaiement.valueOf(rs.getString("mode_paiement")))
                .membreId(rs.getLong("membre_id"))
                .cotisationId(rs.getLong("cotisation_id"))
                .build();
    }
}