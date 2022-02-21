package com.bookxchange.repositories;

import com.bookxchange.model.Transaction;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;

public class TransactionRepo {


  public void createTransaction(Transaction rentTransaction) throws SQLException {

    String sql = "INSERT INTO Transaction ( marketBookId, memberIdFrom, memberIdTo, transactionType, transactionDate, expectedReturnDate)"
                + "values(?,?,?,?,?,?)";
    System.out.println(sql);
    try (Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, rentTransaction.getMarketBookId().toString());
      ps.setString(2, rentTransaction.getMemberIdFrom().toString());
      ps.setString(3, rentTransaction.getMemberIdTo().toString());
      ps.setString(4, rentTransaction.getTransactionType().toString());
      ps.setDate(5, Date.valueOf(rentTransaction.getTransactionDate()));
      ps.setDate(6, Date.valueOf(rentTransaction.getExpectedReturnDate()));
      ps.executeUpdate();
//      ps.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


//  private Book buyBook(MarketBook marketBook) {
//
//    // creare de transaction  object ( ai grija la cei 2 constructori , trb 2 diferiti )
//    // get de membru din baza de date -> scade la member nr de points si fa update in baza de date
//    return null;
//  }
}
