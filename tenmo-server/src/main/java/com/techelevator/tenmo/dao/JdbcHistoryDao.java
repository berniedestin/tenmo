package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.History;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcHistoryDao implements HistoryDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcHistoryDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public History createHistory(History history) {
        History newHistory = null;

        String sql = "INSERT INTO transaction_history(from_id, to_id, amount, status)\n" +
                "VALUES(?,?,?,?) RETURNING transaction_id;";

        try{
            int newHistoryId = jdbcTemplate.queryForObject(sql,int.class,
                    history.getFromId(),history.getToId(), history.getAmount(),history.getStatus());
            newHistory = getHistoryById(newHistoryId);

        }catch (Exception e){
            System.out.println("Something went wrong creating a new History");
        }

        return newHistory;
    }

    @Override
    public History getHistoryById(int id) {
        History history = null;

        String sql = "select transaction_id, transaction_date, from_id, to_id, amount, status \n" +
                "from transaction_history\n" +
                "WHERE transaction_id = ?;";
        try{

            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
            if(result.next()){
                history = mapRowsToHistory(result);
            }

        }catch (Exception e){
            System.out.println("Something went wrong retrieving the History");
        }

        return history;
    }

    @Override
    public List<History> getHistoryByUser(int userId) {
        List<History> histories = new ArrayList<>();

        String sql = "select transaction_id, transaction_date, from_id, to_id, amount, status \n" +
                "from transaction_history\n" +
                "WHERE from_id = ?\n" +
                "OR to_id = ?;";

        try {

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId,userId);
            while (results.next()){
                histories.add(mapRowsToHistory(results));
            }

        }catch (Exception e){
            System.out.println("Something went wrong with getHistoryByUser");
        }

        return histories;
    }

    @Override
    public History updateHistory(History history) {
        History updatedHistory = null;

        String sql = "UPDATE transaction_history\n" +
                "SET transaction_date = ?, from_id = ?, to_id = ?, amount = ?, status = ?\n" +
                "WHERE transaction_id =?;";

        try{

            int numberOfRows = jdbcTemplate.update(sql, history.getTransactionDate(), history.getFromId(),
                    history.getToId(), history.getAmount(), history.getStatus(), history.getTransactionId());

            if(numberOfRows == 0){
                throw new Exception();
            } else {
                updatedHistory = getHistoryById(history.getTransactionId());
            }

        }catch (Exception e){
            System.out.println("Something went wrong with updateHistory");
        }

        return updatedHistory;
    }

    @Override
    public int deleteHistoryById(int id) {
        int numberOfRows = 0;

        String sql = "DELETE\n" +
                "from transaction_history\n" +
                "WHERE transaction_id = ?;";

        try{
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (Exception e){
            System.out.println("Something went wrong with deleteHistoryById");
        }


        return numberOfRows;
    }

    public History mapRowsToHistory(SqlRowSet rowSet){
        History history = new History();
        history.setTransactionId(rowSet.getInt("transaction_id"));
        history.setTransactionDate(rowSet.getDate("transaction_date").toLocalDate());
        history.setFromId(rowSet.getInt("from_id"));
        history.setToId(rowSet.getInt("to_id"));
        history.setAmount(rowSet.getBigDecimal("amount"));
        history.setStatus(rowSet.getString("status"));
        return history;
    }
}
