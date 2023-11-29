package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    public Account createAccount(Account account);

    public Account getAccountById(int id);

    public Account getAccountByName(String name);

    public Account updateAccount(Account account);

    public int deleteAccountById(int id);



}
