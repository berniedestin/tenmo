package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
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
import java.security.Principal;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Account getById(Principal principal){

        int getById = userDao.findIdByUsername(principal.getName());

        Account account = accountDao.getAccountByUserId(getById);

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return account;
        }






    }


}
