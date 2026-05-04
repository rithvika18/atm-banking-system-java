package com.project;


import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ATMServices atmService = new ATMServices();
    static Account loggedInAccount = null;

    public static void main(String[] args) {
        while (true) {
            if (loggedInAccount == null) {
                showMainMenu();
            } else {
                showATMMenu();
            }
        }
    }

    static void showMainMenu() {
        System.out.println("\n==========================");
        System.out.println("   ATM BANKING SYSTEM    ");
        System.out.println("==========================");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice == 1) createAccount();
        else if (choice == 2) login();
        else if (choice == 3) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }

    static void createAccount() {
        System.out.print("Enter your name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Set 4 digit PIN: ");
        String pin = sc.next();
        System.out.print("Initial deposit amount: ");
        double amount = sc.nextDouble();
        String accNum = atmService.createAccount(name, pin, amount);
        if (accNum != null) {
            System.out.println("\nAccount created successfully!");
            System.out.println("Your Account Number: " + accNum);
            System.out.println("Keep it safe!");
        }
    }

    static void login() {
        System.out.print("Enter Account Number: ");
        String accNum = sc.next();
        System.out.print("Enter PIN: ");
        String pin = sc.next();
        loggedInAccount = atmService.login(accNum, pin);
        if (loggedInAccount != null) {
            System.out.println("\nWelcome " + 
                loggedInAccount.getHolderName() + "!");
        } else {
            System.out.println("Invalid account number or PIN!");
        }
    }

    static void showATMMenu() {
        System.out.println("\n==========================");
        System.out.println("Welcome " + 
            loggedInAccount.getHolderName());
        System.out.println("==========================");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transaction History");
        System.out.println("5. Logout");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice == 1) checkBalance();
        else if (choice == 2) deposit();
        else if (choice == 3) withdraw();
        else if (choice == 4) transactionHistory();
        else if (choice == 5) {
            loggedInAccount = null;
            System.out.println("Logged out successfully!");
        }
    }

    static void checkBalance() {
        System.out.println("\nCurrent Balance: Rs." + 
            loggedInAccount.getBalance());
    }

    static void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();
        if (atmService.deposit(loggedInAccount, amount)) {
            System.out.println("Deposited Rs." + amount);
            System.out.println("New Balance: Rs." + 
                loggedInAccount.getBalance());
        }
    }

    static void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = sc.nextDouble();
        if (atmService.withdraw(loggedInAccount, amount)) {
            System.out.println("Withdrawn Rs." + amount);
            System.out.println("New Balance: Rs." + 
                loggedInAccount.getBalance());
        }
    }

    static void transactionHistory() {
        List<Transaction> list = 
            atmService.getHistory(loggedInAccount);
        System.out.println("\n--- Last 10 Transactions ---");
        if (list.isEmpty()) {
            System.out.println("No transactions found!");
        } else {
            for (Transaction t : list) {
                System.out.println(t.getDate() + " | " +
                    t.getTransactionType() + " | Rs." + 
                    t.getAmount());
            }
        }
    }
}