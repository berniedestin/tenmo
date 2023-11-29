package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account createAccount(Account account) {
        return null;
    }

    @Override
    public Account getAccountById(int id) {
        Account account = null;

        String sql = "select account_id, user_id, balance \n" +
                "from account\n" +
                "WhERE account_id = ?;";

        try{

            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if( result.next()){
                account = mapRowsToAccount(result);
            }


        }catch (Exception e){
            System.out.println("Something went wrong in getAccountById");
        }


        return account;
    }

    @Override
    public Account getAccountByName(String name) {
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }

    @Override
    public int deleteAccountById(int id) {
        return 0;
    }

    public Account mapRowsToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        // Should only have one of these active, not sure which one will work
        //account.setBalanceDouble(rowSet.getDouble("balance"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }
}
