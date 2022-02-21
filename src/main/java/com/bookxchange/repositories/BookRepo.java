package com.bookxchange.repositories;

import com.bookxchange.dto.BookDto;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepo {

  public List<BookDto> getAllBooksEver() throws SQLException {
    String sql =
        "SELECT\n"
            + "    books.title,\n"
            + "    Authors.name,\n"
            + "    Authors.surname,\n"
            + "    books.quantity\n"
            + "    FROM\n"
            + "    books\n"
            + "    JOIN\n"
            + "    Authors ON Authors.id = books.author";
    List<BookDto> books = new ArrayList<>();
    try (Connection con = JdbcConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          books.add(
              new BookDto(
                  rs.getString("books.title"),
                  rs.getString("Authors.name"),
                  rs.getString("Authors.surname"),
                  rs.getInt("books.quantity")));
        }
      }
    }
    return books;
  }
}
