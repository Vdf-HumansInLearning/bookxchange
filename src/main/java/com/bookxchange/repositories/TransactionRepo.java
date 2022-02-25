package com.bookxchange.repositories;

import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.Transaction;
import utils.JdbcConnection;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;


public class TransactionRepo {


    public void createTransaction(Transaction transaction) throws SQLException {
        String sql = null;

        if(transaction.getTransactionType().equals(TransactionType.SOLD)){
            sql= "INSERT INTO Transaction ( marketBookId, memberIdFrom, memberIdTo, transactionType, transactionDate)"
                    + "values(?,?,?,?,?)";
        }else {
            sql= "INSERT INTO Transaction ( marketBookId, memberIdFrom, memberIdTo, transactionType, transactionDate, expectedReturnDate)"
                    + "values(?,?,?,?,?,?)";
        }

        System.out.println(sql);
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, transaction.getMarketBookId().toString());
            ps.setString(2, transaction.getMemberIdFrom().toString());
            ps.setString(3, transaction.getMemberIdTo().toString());
            ps.setString(4, transaction.getTransactionType().toString());
            ps.setDate(5, Date.valueOf(transaction.getTransactionDate()));
            if(transaction.getTransactionType().equals(TransactionType.RENT)){
                ps.setDate(6, Date.valueOf(transaction.getExpectedReturnDate()));
            }

            ps.executeUpdate();
//      ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Transaction getTransactionByBookIdAndLeftBy(UUID marketBookId, UUID memberIdTo) throws SQLException, IOException {

        String sql = "SELECT * FROM Transaction WHERE\n" +
                "\tTransaction.marketBookId ='" + marketBookId + "' AND Transaction.memberIdTo ='" + memberIdTo + "';";
        return getTransaction(sql);
    }

    public Transaction getTransactionByWhoSelleddAndWhoBuys(UUID memberIdFrom, UUID memberIdTo) throws SQLException, IOException {

        String sql = "SELECT * FROM Transaction WHERE\n" +
                "\tTransaction.memberIdFrom ='" + memberIdFrom + "' AND Transaction.memberIdTo ='" + memberIdTo + "';";
        return getTransaction(sql);
    }

    private Transaction getTransaction(String sql) throws SQLException {
        Transaction transaction = new Transaction();
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    transaction.setId(rs.getLong("id"));
                    transaction.setMarketBookId(UUID.fromString(rs.getString("marketBookId")));
                    transaction.setMemberIdFrom(UUID.fromString(rs.getString("memberIdFrom")));
                    transaction.setMemberIdTo(UUID.fromString(rs.getString("memberIdTo")));

                }
            }
        }
        return transaction;
    }

}
