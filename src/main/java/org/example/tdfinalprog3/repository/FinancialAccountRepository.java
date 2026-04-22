package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.FinancialAccount;
import org.example.tdfinalprog3.model.enums.AccountType;
import org.example.tdfinalprog3.model.enums.Bank;
import org.example.tdfinalprog3.model.enums.MobileBankingService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FinancialAccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public FinancialAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FinancialAccount save(FinancialAccount account) {
        String sql = "INSERT INTO financial_accounts (id, account_type, holder_name, bank_name, bank_code, " +
                "branch_code, account_number, account_key, mobile_service, phone_number, balance, collectivity_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "balance = EXCLUDED.balance";
        jdbcTemplate.update(sql,
                account.getId(),
                account.getAccountType() != null ? account.getAccountType().name() : null,
                account.getHolderName(),
                account.getBankName() != null ? account.getBankName().name() : null,
                account.getBankCode(),
                account.getBranchCode(),
                account.getAccountNumber(),
                account.getAccountKey(),
                account.getMobileService() != null ? account.getMobileService().name() : null,
                account.getPhoneNumber(),
                account.getBalance(),
                account.getCollectivityId()
        );
        return account;
    }

    public Optional<FinancialAccount> findById(String id) {
        String sql = "SELECT * FROM financial_accounts WHERE id = ?";
        List<FinancialAccount> accounts = jdbcTemplate.query(sql, new FinancialAccountRowMapper(), id);
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
    }

    public List<FinancialAccount> findByCollectivityId(String collectivityId) {
        String sql = "SELECT * FROM financial_accounts WHERE collectivity_id = ?";
        return jdbcTemplate.query(sql, new FinancialAccountRowMapper(), collectivityId);
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM financial_accounts WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private static class FinancialAccountRowMapper implements RowMapper<FinancialAccount> {
        @Override
        public FinancialAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            FinancialAccount account = new FinancialAccount();
            account.setId(rs.getString("id"));

            String accountTypeStr = rs.getString("account_type");
            if (accountTypeStr != null) {
                account.setAccountType(AccountType.valueOf(accountTypeStr));
            }

            account.setHolderName(rs.getString("holder_name"));

            String bankNameStr = rs.getString("bank_name");
            if (bankNameStr != null) {
                account.setBankName(Bank.valueOf(bankNameStr));
            }

            account.setBankCode(rs.getString("bank_code"));
            account.setBranchCode(rs.getString("branch_code"));
            account.setAccountNumber(rs.getString("account_number"));
            account.setAccountKey(rs.getString("account_key"));

            String mobileServiceStr = rs.getString("mobile_service");
            if (mobileServiceStr != null) {
                account.setMobileService(MobileBankingService.valueOf(mobileServiceStr));
            }

            account.setPhoneNumber(rs.getString("phone_number"));
            account.setBalance(rs.getDouble("balance"));
            account.setCollectivityId(rs.getString("collectivity_id"));
            return account;
        }
    }
}