package com.bookxchange.repositories;

import com.bookxchange.dto.MarketBookDto;
import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import utils.JdbcConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MarketBookRepo {


    public List<MarketBookDto> getAllMarketBook() throws SQLException, IOException {


        String sql = "SELECT books.title, Authors.name, Authors.surname, BookMarket.forSell, BookMarket.sellPrice, BookMarket.forRent, BookMarket.rentPrice, BookMarket.bookState, BookMarket.bookStatus, Members.username FROM BookMarket JOIN Members ON Members.userID = BookMarket.userID JOIN books ON books.isbn = BookMarket.bookID JOIN  Authors ON Authors.id = books.author";
        List<MarketBookDto> marketBooks = new ArrayList<>();
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    marketBooks.add(new MarketBookDto(rs.getString("books.title"),
                        rs.getString("Authors.name"),
                        rs.getString("Authors.surname"),
                        rs.getBoolean("BookMarket.forSell"),
                        rs.getDouble("BookMarket.sellPrice"),
                        rs.getBoolean("BookMarket.forRent"),
                        rs.getDouble("BookMarket.rentPrice"),
                        BookState.valueOf(rs.getString("BookMarket.bookState")),
                        BookStatus.valueOf(rs.getString("BookMarket.bookStatus")),
                        rs.getString("Members.username")));

                }
            }
        }
        return marketBooks;
    }

    public List<MarketBookDto> getAllMarketBookByBookStatus(BookStatus bookStatus) throws SQLException {
        String sql = "SELECT books.title, Authors.name, Authors.surname, BookMarket.forSell, BookMarket.sellPrice, BookMarket.forRent, BookMarket.rentPrice, BookMarket.bookState, BookMarket.bookStatus, Members.username FROM\n" +
            "    BookMarket\n" +
            "        JOIN\n" +
            "    Members ON Members.userID = BookMarket.userID\n" +
            "        JOIN\n" +
            "    books ON books.isbn = BookMarket.bookID\n" +
            "        JOIN\n" +
            "    Authors ON Authors.id = books.author\n" +
            "WHERE\n" +
            "\tBookMarket.bookStatus ='" + bookStatus + "'";
        List<MarketBookDto> marketBooks = new ArrayList<>();
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    marketBooks.add(new MarketBookDto(rs.getString("books.title"),
                        rs.getString("Authors.name"),
                        rs.getString("Authors.surname"),
                        rs.getBoolean("BookMarket.forSell"),
                        rs.getDouble("BookMarket.sellPrice"),
                        rs.getBoolean("BookMarket.forRent"),
                        rs.getDouble("BookMarket.rentPrice"),
                        BookState.valueOf(rs.getString("BookMarket.bookState")),
                        BookStatus.valueOf(rs.getString("BookMarket.bookStatus")),
                        rs.getString("Members.username")));

                }
            }
        }
        return marketBooks;
    }


    public MarketBookDto getAllMarketBookById(UUID id) throws SQLException {
        String sql = "SELECT books.title, Authors.name, Authors.surname, BookMarket.forSell, BookMarket.sellPrice, BookMarket.forRent, BookMarket.rentPrice, BookMarket.bookState, BookMarket.bookStatus, Members.username FROM\n" +
            "    BookMarket\n" +
            "        JOIN\n" +
            "    Members ON Members.userID = BookMarket.userID\n" +
            "        JOIN\n" +
            "    books ON books.isbn = BookMarket.bookID\n" +
            "        JOIN\n" +
            "    Authors ON Authors.id = books.author\n" +
            "WHERE\n" +
            "\tBookMarket.id ='" + id + "'";
        MarketBookDto marketBookDto = null;
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    marketBookDto = new MarketBookDto(rs.getString("books.title"),
                        rs.getString("Authors.name"),
                        rs.getString("Authors.surname"),
                        rs.getBoolean("BookMarket.forSell"),
                        rs.getDouble("BookMarket.sellPrice"),
                        rs.getBoolean("BookMarket.forRent"),
                        rs.getDouble("BookMarket.rentPrice"),
                        BookState.valueOf(rs.getString("BookMarket.bookState")),
                        BookStatus.valueOf(rs.getString("BookMarket.bookStatus")),
                        rs.getString("Members.username"));

                }
            }
        }
        return marketBookDto;
    }

}
