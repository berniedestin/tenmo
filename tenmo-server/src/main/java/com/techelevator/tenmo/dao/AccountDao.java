package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface AccountDao {
    public Account createAccount(int userId);

    public Account getAccountById(int id);

    public Account getAccountByUserId(int userId);

    public Account updateAccount(Account account);

    public int deleteAccountById(int id);



}
