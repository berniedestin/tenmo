package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    //baseURL
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;
    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Account getAccount(){
        Account account = null;
        String url = baseUrl + "/account";
        try {
            ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (Exception ex){
            System.out.println("No account found");
        }
        return account;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
