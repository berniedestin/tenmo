package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.History;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class TransactionService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public TransactionService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<User> listUsers(){
        List<User> users = null;

        String url = baseUrl + "transaction/list_users";

        try{
            ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), User[].class);
            users = Arrays.asList(response.getBody());

        }catch (Exception e){
            System.out.println("Something went wrong getting the list of users");
        }
        return users;
    }

    public History transfer(int toUserId, BigDecimal amount){
        History transaction = null;
        String url = baseUrl + "transaction/" + toUserId + "/" + amount.doubleValue();

        try{
            ResponseEntity<History> response = restTemplate.exchange(url, HttpMethod.POST, makeAuthEntity(), History.class);
            transaction = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            errorSout(e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong with the transfer");
        }
        return transaction;
    }

    public List<History> viewTransferHistory(){

        List<History> transferHistory = null;

        String url = baseUrl + "/transaction";

        try {
            ResponseEntity<History[]> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), History[].class);
            transferHistory = Arrays.asList(response.getBody());
        }catch (Exception e){
            System.out.println("Something went wrong with retrieving transfer history");
        }
        return transferHistory;
    }

    public List<History> viewPendingTransactions(){
        List<History> pendingHistory = null;

        String url = baseUrl + "/transaction/request/pending";

        try{

            ResponseEntity<History[]> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), History[].class);

            pendingHistory = Arrays.asList(response.getBody());

        }catch (Exception e){
            System.out.println("Something went wrong viewing pending transactions");
        }

        return pendingHistory;
    }


    public History requestMoney(int fromId, BigDecimal amount){
        History history =  null;
        String url = baseUrl + "/transaction/request/" + fromId + "/" + amount.doubleValue();
        try{
            ResponseEntity<History> response = restTemplate.exchange(url, HttpMethod.POST, makeAuthEntity(), History.class);
            history = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            errorSout(e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong with the transfer");
        }

        return history;
    }

    public History approveRequest(int transactionId){
        History approvedRequest = null;

        String url = baseUrl + "/transaction/request/approve/" + transactionId;

        try{

            ResponseEntity<History> response = restTemplate.exchange(url, HttpMethod.PUT, makeAuthEntity(), History.class);
            approvedRequest = response.getBody();

        }catch (RestClientResponseException | ResourceAccessException e) {
            errorSout(e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong with the approval");
        }

        return approvedRequest;
    }

    public History rejectRequest(int transactionId){
        History rejectRequest = null;

        String url = baseUrl + "/transaction/request/reject/" + transactionId;

        try{
            ResponseEntity<History> response = restTemplate.exchange(url, HttpMethod.PUT, makeAuthEntity(), History.class);
            rejectRequest = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            errorSout(e.getMessage());
        } catch (Exception e){
            System.out.println("Something went wrong with the rejection");
        }

        return rejectRequest;
    }




    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public void errorSout(String errorJsonString){
        int indexStart = errorJsonString.indexOf("\"message\":\"") + 11;
        int indexEnd = errorJsonString.indexOf("\",\"path\":");
        System.out.println(errorJsonString.substring(indexStart, indexEnd));
    }


}
