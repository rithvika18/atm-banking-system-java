package com.project;


import java.sql.*;
import java.util.Random;

public class AccountDAO {
    public String createAccount(String name, String pin, 
                                 double initialBalance) {
        String accNum = generateAccountNumber();
        String sql = "INSERT INTO accounts VALUES (?,?,?,?,NOW())";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNum);
            ps.setString(2, name);
            ps.setString(3, pin);
            ps.setDouble(4, initialBalance);
            ps.executeUpdate();
            return accNum;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Account login(String accNum, String pin) {
        String sql = "SELECT * FROM accounts WHERE " +
                     "account_number=? AND pin=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNum);
            ps.setString(2, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getString("account_number"),
                    rs.getString("holder_name"),
                    rs.getString("pin"),
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public boolean updateBalance(String accNum, double balance) {
        String sql = "UPDATE accounts SET balance=? " +
                     "WHERE account_number=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, balance);
            ps.setString(2, accNum);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        return String.valueOf(1000000000L + 
               (long)(rand.nextDouble() * 9000000000L))
               .substring(0, 10);
    }
}