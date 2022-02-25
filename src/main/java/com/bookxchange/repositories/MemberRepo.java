package com.bookxchange.repositories;

import com.bookxchange.model.MarketBook;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class MemberRepo {

    public void updatePointsToMember(MarketBook marketBook) {

        String sql = String.format("UPDATE Members SET points = points+%s  WHERE userID = '%s' ", 10, marketBook.getUserId().toString());
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int convertMoneyToPoints(MarketBook marketBook){
        //        1 RON =  10 points ;
       return  (int) (marketBook.getSellPrice()/10);
    }


    public void updatePointsAfterBuy(MarketBook marketBook, UUID userIdWhoBuys) {

        String sql = String.format("UPDATE Members SET points = points-%s  WHERE userID = '%s' ",convertMoneyToPoints(marketBook) , userIdWhoBuys.toString());
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  int getPointsForMember(UUID memberID) {
        String sql = String.format( "SELECT points FROM Members Where userID = '%s'", memberID) ;
        int points = 0;
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    points = rs.getInt("points");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

}
