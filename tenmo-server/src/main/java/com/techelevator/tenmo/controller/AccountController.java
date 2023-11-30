package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountDao accountDao;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Account getById(@PathVariable int id){
        Account account = accountDao.getAccountById(id);

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return account;
        }
        //change this to work with the principal instead of the ID
        //that way you don't pass another person information by id

        // /account  - as long as you are logged in - it will give you your acct info.
        //GET - good practice to commit more often than you need too



    }


}
