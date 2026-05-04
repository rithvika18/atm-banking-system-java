package com.project;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void addTransaction(String accNum, 
                                String type, double amount) {
        String sql = "INSERT INTO transactions " +
                     "(account_number, transaction_type, " +
                     "amount, transaction_date) VALUES (?,?,?,NOW())";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNum);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Transaction> getHistory(String accNum) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions " +
                     "WHERE account_number=? " +
                     "ORDER BY transaction_date DESC LIMIT 10";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
                    rs.getString("account_number"),
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getString("transaction_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }
}
