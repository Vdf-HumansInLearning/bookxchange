package com.bookxchange.repositories;

import com.bookxchange.dto.BookDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepo {

    static final String DB_URL = "jdbc:mysql://localhost:3306/bookOLX";
    static final String USER = "root";
    static final String PASS = "root";


    public List<BookDto> getAllBooksEver() throws SQLException {
        String sql = "SELECT\n" +
            "    books.title,\n" +
            "    Authors.name,\n" +
            "    Authors.surname,\n" +
            "    books.quantity\n" +
            "    FROM\n" +
            "    books\n" +
            "    JOIN\n" +
            "    Authors ON Authors.id = books.author";
        List<BookDto> books = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(new BookDto(rs.getString("books.title"),
                        rs.getString("Authors.name"),
                        rs.getString("Authors.surname"),
                        rs.getInt("books.quantity")));
                }
            }
        }
        return books;
    }

}


