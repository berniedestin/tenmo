package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransactionService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    public TransactionService(String url) {
        this.baseUrl = url;
    }
}
