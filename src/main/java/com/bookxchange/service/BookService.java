package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidISBNException;
import com.bookxchange.dto.BookDto;
import com.bookxchange.model.Author;
import com.bookxchange.model.Book;
import com.bookxchange.repositories.BookRepo;
import utils.JdbcConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public class BookService {


    public static void main(String[] args) throws SQLException, IOException {


        BookRepo bookRepo = new BookRepo();
        List<BookDto> allBooksEver = bookRepo.getAllBooksEver();

        for (BookDto book : allBooksEver) {
            System.out.println(book);
        }
    }


    public void addBookToDB(String isbn, boolean rent, double rentPrice, boolean sell, double sellPrice, long userID) throws SQLException {
        IsbnService isbnChecker = new IsbnService();

//        Check If Book is in database.
        String sqlSeeIfBookIsPresent = "SELECT\n" +
                "books.isbn\n" +
                "FROM\n" +
                "books\n" +
                "WHERE\n" +
                "books.isbn = isbn";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSeeIfBookIsPresent)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    addMarketBookOnly(isbn, rent, rentPrice, sell, sellPrice, userID);
                } else {
//                    addBookAndMarketBook();
                }
            }
        }

//        If present add just user entry and update quantity
        String sqlRetrievedQuantity = "SELECT\n" +
                "books.quantity\n" +
                "FROM\n" +
                "books\n" +
                "WHERE\n" +
                "books.isbn = isbn";


    }

    public void addBookAndMarketBook(String isbn, boolean rent, double rentPrice, boolean sell, double sellPrice, long userID) throws SQLException {

//        Check if author is present in database.


//        add book
        IsbnService isbnBookRetriever = new IsbnService();
        Book bookToAdd = isbnBookRetriever.hitIsbnBookRequest(isbn);
        if(bookToAdd.equals(null)){
            throw new InvalidISBNException(isbn + " is invalid");
        } else {
            addingBookToDataBase(bookToAdd);
          ListIterator<Author> iterateAuthors = bookToAdd.getAuthors().listIterator();

          while (iterateAuthors.hasNext()) {
              checkAuthorUpdateOrAddDB(iterateAuthors.next());
          }




        }

//        add author/s

//        add user enry

    }

    public void addMarketBookOnly(String isbn, boolean rent, double rentPrice, boolean sell, double sellPrice, long userID) {

    }

    public void addingBookToDataBase(Book addedBook) throws SQLException{
        String sqlForInsertingNewBook = "INSERT INTO\n" +
                "books\n" +
                "(isbn,title,description,quantity)\n" +
                "VALUE(addedBook.getIsbn(),addedBook.getTitle(),addedBook.getDescription(),1);\n";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewBook)) {
            ps.executeQuery();
        }
    }

    private void addAuthor(Author authorToAdd) throws SQLException{
        UUID authorIDToAdd = authorToAdd.getId();
        String authorNameToAdd = authorToAdd.getName();
        String authorSurnameToAdd = authorToAdd.getSurname();


        String sqlForInsertingNewAuthor = "INSERT INTO" +
                "Authors" +
                "(id, name, surname)" +
                "(authorIDToAdd, authorToAdd.getName(), authorToAdd.getSurname())";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewAuthor)) {
             ps.executeQuery();
        }

    }



    private void checkAuthorUpdateOrAddDB(Author checkedAuthor) throws SQLException {

        String sqlCheckAuthorExistsUpdateUUID = "SELECT\n" +
                "Authors.id\n" +
                "FROM\n" +
                "Authors\n" +
                "WHERE\n" +
                "Authors.name = chececkedAuthor.getName() \n" +
                "Authors.surname = checkedAuthor.getSurname()";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlCheckAuthorExistsUpdateUUID)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.isBeforeFirst()) {
                        checkedAuthor.setId(UUID.fromString(rs.getString("Authors.id")));
                    } else {
                    checkedAuthor.setId(UUID.randomUUID());
                    addAuthor(checkedAuthor);
                }
            }
        }
    }




}