package com.bookxchange.repositories;

import org.springframework.stereotype.Component;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RatingRepo {

    public void addRating(int grade, String description, String leftBy, String userID, String bookID) throws SQLException {

        String sql = "INSERT INTO Rating(grade,description,leftBy,userID,bookID) " + "VALUES(?,?,?,?,?)";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, grade);
            ps.setString(2, description);
            ps.setString(3, leftBy);
            ps.setString(4, userID);
            ps.setString(5, bookID);
            ps.executeUpdate();
        }
    }
}
