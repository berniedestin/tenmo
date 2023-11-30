package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class AccountService {

    //baseURL
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    public AccountService(String url) {
        this.baseUrl = url;
    }

    public AccountService getAccount(){


    }


}
