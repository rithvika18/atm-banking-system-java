package com.project;



public class Transaction {
    private String accountNumber;
    private String transactionType;
    private double amount;
    private String date;

    public Transaction(String accountNumber, String transactionType,
                       double amount, String date) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}