package com.bookxchange.repositories;

import com.bookxchange.model.MarketBook;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRepo {

    public void updatePointsToMember(MarketBook marketBook) {
    System.out.println("meme " + marketBook.getUserId().toString());
        String sql = String.format("UPDATE Members SET points = points + %s  WHERE userID = '%s' ", 10 ,marketBook.getUserId().toString());
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
