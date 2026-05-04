package com.project;


import java.util.List;

public class ATMServices {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public String createAccount(String name, String pin, 
                                 double initialBalance) {
        return accountDAO.createAccount(name, pin, initialBalance);
    }

    public Account login(String accNum, String pin) {
        return accountDAO.login(accNum, pin);
    }

    public boolean deposit(Account account, double amount) {
        double newBalance = account.getBalance() + amount;
        if (accountDAO.updateBalance(
                account.getAccountNumber(), newBalance)) {
            account.setBalance(newBalance);
            transactionDAO.addTransaction(
                account.getAccountNumber(), "DEPOSIT", amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(Account account, double amount) {
        if (account.getBalance() < amount) {
            System.out.println("Insufficient balance!");
            return false;
        }
        double newBalance = account.getBalance() - amount;
        if (accountDAO.updateBalance(
                account.getAccountNumber(), newBalance)) {
            account.setBalance(newBalance);
            transactionDAO.addTransaction(
                account.getAccountNumber(), "WITHDRAWAL", amount);
            return true;
        }
        return false;
    }

    public List<Transaction> getHistory(Account account) {
        return transactionDAO.getHistory(account.getAccountNumber());
    }
}
