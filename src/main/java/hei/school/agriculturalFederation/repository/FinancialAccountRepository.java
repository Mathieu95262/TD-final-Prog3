package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.AccountType;
import hei.school.agriculturalFederation.model.FinancialAccount;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FinancialAccountRepository {

    private final DataSourceConfig dataSourceConfig;

    public FinancialAccountRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private FinancialAccount mapRow(ResultSet rs) throws SQLException {
        FinancialAccount a = new FinancialAccount();
        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setAccountType(AccountType.valueOf(rs.getString("account_type")));
        a.setAccountHolder(rs.getString("account_holder"));
        a.setBankName(rs.getString("bank_name"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setMobileHolder(rs.getString("mobile_holder"));
        a.setMobileService(rs.getString("mobile_service"));
        a.setMobilePhone(rs.getString("mobile_phone"));
        a.setCurrentBalance(rs.getLong("initial_balance"));
        return a;
    }

    public FinancialAccount save(FinancialAccount account) {
        String sql = """
                INSERT INTO financial_account
                  (id, collectivity_id, account_type, account_holder, bank_name, account_number,
                   mobile_holder, mobile_service, mobile_phone, initial_balance)
                VALUES (?, ?, CAST(? AS account_type_enum), ?, ?, ?, ?, ?, ?, ?)
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getId());
            ps.setString(2, account.getCollectivityId());
            ps.setString(3, account.getAccountType().name());
            ps.setString(4, account.getAccountHolder());
            ps.setString(5, account.getBankName());
            ps.setString(6, account.getAccountNumber());
            ps.setString(7, account.getMobileHolder());
            ps.setString(8, account.getMobileService());
            ps.setString(9, account.getMobilePhone());
            ps.setLong(10, account.getCurrentBalance());
            ps.executeUpdate();
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving financial account: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public List<FinancialAccount> findAllByCollectivityId(String collectivityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM financial_account WHERE collectivity_id = ?")) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            List<FinancialAccount> accounts = new ArrayList<>();
            while (rs.next()) accounts.add(mapRow(rs));
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding financial accounts: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public Optional<FinancialAccount> findById(String id) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM financial_account WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding financial account by id: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /**
     * Computes balance = initial_balance + SUM(CREDIT) - SUM(DEBIT)
     * for all transactions up to and including the given date.
     */
    public long getBalanceAt(String accountId, LocalDate at) {
        String sql = """
                SELECT fa.initial_balance +
                       COALESCE(SUM(CASE WHEN ft.transaction_type = 'CREDIT' THEN ft.amount ELSE -ft.amount END), 0)
                FROM financial_account fa
                LEFT JOIN financial_transaction ft
                       ON ft.financial_account_id = fa.id
                      AND ft.transaction_date <= ?
                WHERE fa.id = ?
                GROUP BY fa.id, fa.initial_balance
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(at));
            ps.setString(2, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error computing balance: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public boolean hasCashAccount(String collectivityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM financial_account WHERE collectivity_id = ? AND account_type = 'CASH'")) {
            ps.setString(1, collectivityId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking cash account: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
