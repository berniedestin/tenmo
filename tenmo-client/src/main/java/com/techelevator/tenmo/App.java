package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransactionService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private final TransactionService transactionService = new TransactionService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setAuthToken(currentUser.getToken());
            transactionService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        Account account = accountService.getAccount();
        if (account != null){
            System.out.println("Your account balance is: $" + account.getBalance());
        } else {
            System.out.println("No balance available");
        }
	}

	private void viewTransferHistory() {
    List<History> histories = transactionService.viewTransferHistory();
        if (histories != null){
            for (History history : histories){
                System.out.println(history.toString());
            }
        }else {
            System.out.println("No transfer history found");
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        int menuSelection = -1;
        while (true){
            List<User> users = transactionService.listUsers();
            int count = 0;
            for(User user: users){
                count++;
                System.out.println( count + ":  User Id #: " + user.getId() +
                        "  Username: " + user.getUsername());
            }
            System.out.println("0:  Cancel");
            menuSelection = consoleService.promptForMenuSelection("Please select who you would like to send money to: ");
            if(menuSelection > 0 && menuSelection <= count){
                int userId = users.get(menuSelection - 1).getId();
                BigDecimal amount = consoleService.promptForBigDecimal("Please enter an amount to send: ");
                History transfer = transactionService.transfer(userId, amount);
                if(transfer.getStatus() != null) {
                    if (transfer.getStatus().equals("Approved")) {
                        System.out.println("Transfer was successful!");
                        break;
                    } else {
                        System.out.println("Transfer was unsuccessful!");
                        break;
                    }
                } 

            }else if (menuSelection == 0){
                break;
            } else {
                System.out.println("Please enter a valid number!");
            }

        }

		
	}

	private void requestBucks() {
        int menuSelection = -1;
        while (true){
            List<User> users = transactionService.listUsers();
            int count = 0;
            for(User user: users){
                count++;
                System.out.println( count + ":  User Id #: " + user.getId() +
                        "  Username: " + user.getUsername());
            }
            System.out.println("0:  Cancel");
            menuSelection = consoleService.promptForMenuSelection("Please select who you would like to request money from: ");
            if(menuSelection > 0 && menuSelection <= count){
                int userId = users.get(menuSelection - 1).getId();
                BigDecimal amount = consoleService.promptForBigDecimal("Please enter an amount to request: ");
                History transfer = transactionService.requestMoney(userId, amount);
                if(transfer.getStatus() != null) {
                    if (transfer.getStatus().equals("Pending")) {
                        System.out.println("Request is pending");
                        break;
                    } else {
                        System.out.println("Something really went wrong!!!");
                        break;
                    }
                }
            }else if (menuSelection == 0){
                break;
            } else {
                System.out.println("Please enter a valid number!");
            }
        }
	}
}
