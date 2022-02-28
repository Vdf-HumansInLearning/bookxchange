package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidISBNException;
import com.bookxchange.dto.BookDto;
import com.bookxchange.enums.BookState;
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


    public static void main(String[] args) throws SQLException {


        BookRepo bookRepo = new BookRepo();
//        List<BookDto> allBooksEver = bookRepo.getAllBooksEver();
//
//        for (BookDto book : allBooksEver) {
//            System.out.println(book);
//        }

        BookService bookService = new BookService();
        bookService.addingBookToDataBase("9781387207770");
    }


    public void userAddsNewBook(String providedIsbn, boolean rent, double priceToRent, boolean sell, double priceToSell, long recievedUserID, BookState providedBookState) throws SQLException {
        addingBookToDataBase(providedIsbn);
        addingMarketBookForUser(providedIsbn, rent, priceToRent, sell, priceToSell, recievedUserID, providedBookState);
    }


    private void addingBookToDataBase(String isbn) throws SQLException {

//    If a book is present in the DB then this service will not do any process

        System.out.println("sunt aici " + checkIfBookIsInDataBase(isbn));


        if (checkIfBookIsInDataBase(isbn)) {

//            Contacts the ISBN service to retrieve book details
            IsbnService retrivedBook = new IsbnService();



//            Checks and adds Authors from the retrievedBook, if they exist in DB updates them with existing UUID to creat the m2m entry between Book and Author(s)
            Book retrivedBookDetailsFromExternal = retrivedBook.hitIsbnBookRequest(isbn);
            System.out.println(retrivedBook + "ceva");
            Iterator<Author> authorList = retrivedBookDetailsFromExternal.getAuthors().listIterator();
            while (authorList.hasNext()) {
                checkAuthorUpdateOrAddDB(authorList.next());
            }

//            Inserting new book in to the database
            insertBookInDataBase(retrivedBookDetailsFromExternal);

//            Insert m2m entry for book and authors(retrivedBookDetailsFromExternal, authorList)
            insertBookToAuthorsDBEntry(isbn, retrivedBookDetailsFromExternal.getAuthors());


        }

    }


    //    Checks if a book is present in the database
    private boolean checkIfBookIsInDataBase(String isbn) throws SQLException {

        String sqlSeeIfBookIsPresent = "SELECT\n" +
                "books.isbn\n" +
                "FROM\n" +
                "books\n" +
                "WHERE\n" +
                "books.isbn = isbn";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSeeIfBookIsPresent)) {

//            if present will execute method to update quantity by 1
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.isBeforeFirst()) {

                    System.out.println("aici ");
                    updateQuantityForExistingBook(isbn);
                    return true;
                } else {
                    System.out.println("sau aici ");
                    return false;
                }
            }
        }
    }


    //    If called method updates book quantity by 1
    private void updateQuantityForExistingBook(String isbn) throws SQLException {

        String sqlUpdateQuantity = "UPDATE\n" +
                "books\n" +
                "SET\n" +
                "quantity = quantity+1\n" +
                "WHERE\n" +
                "books.isbn = isbn";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdateQuantity)) {
            ps.executeUpdate(sqlUpdateQuantity);
        }
    }


    //    Veryfies checked author if is present in the database by matching both name and surname
//    On match updates the checked author id from the DB to be used in the users book entry flow
//    If not found sets a UUID and adds given author to the database
    private void checkAuthorUpdateOrAddDB(Author checkedAuthor) throws SQLException {

        System.out.println(checkedAuthor.getSurname() + " checkedAuthor");
        System.out.println(checkedAuthor.getName() + " checkedAuthor");

         String x = checkedAuthor.getSurname();
        String y = checkedAuthor.getName();



        String sqlCheckAuthorExistsUpdateUUID = "SELECT\n" +
                "Authors.id\n" +
                "FROM\n" +
                "Authors\n" +
                "WHERE\n" +
                "Authors.name ="+ "'"+y +"' AND " +
                "Authors.surname =" +"\'"+x +"\';";

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


    //    Method adds provided author to the database
    private void addAuthor(Author authorToAdd) throws SQLException {


        String sqlForInsertingNewAuthor = "INSERT INTO " +
                "Authors " +
                "(id, name, surname)" +
                " VALUES (" +"\'"  +
                authorToAdd.getId() + "\', \'"
                + authorToAdd.getName() + "\' , \'"+ authorToAdd.getSurname() + "\');";

        System.out.println(sqlForInsertingNewAuthor + " string final");

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewAuthor)) {
            ps.executeQuery();
        }

    }


    private void insertBookInDataBase(Book bookToInsert) throws SQLException {

//        bookToInsert.getIsbn(), bookToInsert.getTitle(), bookToInsert.getDescription()
        String sqlForInsertingNewBook = "INSERT INTO\n" +
                "books\n" +
                "(isbn,title,description,quantity)\n" +
                "VALUES(bookToInsert.get(),addedBook.getTitle(),addedBook.getDescription(),1);\n";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewBook)) {
            ps.executeQuery();
        }
    }


    private void insertBookToAuthorsDBEntry(String isbn, List<Author> bookAuthors) throws SQLException {

        for (int i = 0; i < bookAuthors.size(); i++) {

            String sqlQueryForLinkingBooksToAuthorsEntry = "INSERT INTO\n" +
                    "author_book_mtm\n" +
                    "(id, book_isbn, author_id)\n" +
                    "VALUES(UUID.randomUUID(), isbn, bookAuthors.get(i).getId())";

            try (Connection con = JdbcConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sqlQueryForLinkingBooksToAuthorsEntry)) {
                ps.executeQuery();
            }
        }
    }


    //    Adds BookMarket entry in DB
    private void addingMarketBookForUser(String providedIsbn, boolean rent, double priceToRent, boolean sell, double priceToSell, long recievedUserID, BookState providedBookState) throws SQLException {

        String sqlQueryForBookMarketEntry = "INSERT INTO\n" +
                "author_book_mtm\n" +
                "(id, userID, bookID, bookState, forSell, sellPrice, forRent, rentPrice, bookStatus)\n" +
                "VALUES(UUID.randomUUID(), recievedUserID, providedIsbn, providedBookState, sell, priceToSell, rent, priceToRent, AVAILABLE)";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQueryForBookMarketEntry)) {
            ps.executeQuery();
        }
    }


}