package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.FinancialTransaction;
import hei.school.agriculturalFederation.model.PaymentMode;
import hei.school.agriculturalFederation.model.TransactionType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FinancialTransactionRepository {

    private final DataSourceConfig dataSourceConfig;

    public FinancialTransactionRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private FinancialTransaction mapRow(ResultSet rs) throws SQLException {
        FinancialTransaction t = new FinancialTransaction();
        t.setId(rs.getString("id"));
        t.setFinancialAccountId(rs.getString("financial_account_id"));
        t.setCollectivityId(rs.getString("collectivity_id"));
        t.setAmount(rs.getLong("amount"));
        t.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));
        t.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
        t.setTransactionDate(rs.getObject("transaction_date", LocalDate.class));
        t.setDescription(rs.getString("description"));
        return t;
    }

    public FinancialTransaction save(FinancialTransaction transaction) {
        String sql = """
                INSERT INTO financial_transaction
                  (id, financial_account_id, collectivity_id, amount, transaction_type, payment_mode, transaction_date, description)
                VALUES (?, ?, ?, ?, CAST(? AS transaction_type_enum), CAST(? AS payment_mode_enum), ?, ?)
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getId());
            ps.setString(2, transaction.getFinancialAccountId());
            ps.setString(3, transaction.getCollectivityId());
            ps.setLong(4, transaction.getAmount());
            ps.setString(5, transaction.getTransactionType().name());
            ps.setString(6, transaction.getPaymentMode().name());
            ps.setDate(7, Date.valueOf(transaction.getTransactionDate()));
            ps.setString(8, transaction.getDescription());
            ps.executeUpdate();
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public List<FinancialTransaction> findByCollectivityAndPeriod(String collectivityId,
                                                                   LocalDate from, LocalDate to) {
        String sql = """
                SELECT * FROM financial_transaction
                WHERE collectivity_id = ?
                  AND transaction_date >= ?
                  AND transaction_date <= ?
                ORDER BY transaction_date DESC
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            List<FinancialTransaction> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
