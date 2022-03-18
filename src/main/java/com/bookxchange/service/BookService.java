package com.bookxchange.service;

import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.model.Author;
import com.bookxchange.model.BookEntity;
import com.bookxchange.model.MarketBook;
import com.bookxchange.repositories.BookRepo;
import org.springframework.stereotype.Service;
import utils.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Service
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


    public void userAddsNewBook(MarketBook providedMarketBook) throws SQLException {
        addingBookToDataBase(providedMarketBook.getBookId());
        addingMarketBookForUser(providedMarketBook.getBookId(), providedMarketBook.isForRent(), providedMarketBook.getRentPrice(), providedMarketBook.isForSell(), providedMarketBook.getSellPrice(), providedMarketBook.getUserId().toString(), providedMarketBook.getBookState());
    }


    private void addingBookToDataBase(String isbn) throws SQLException {
        System.out.println("Reached book adding section");
//    If a book is present in the DB then this service will not do any process
        if (!checkIfBookIsInDataBase(isbn)) {
            System.out.println("Reached the actual run");
//            Contacts the ISBN service to retrieve book details
            IsbnService retrivedBook = new IsbnService();



//            Checks and adds Authors from the retrievedBook, if they exist in DB updates them with existing UUID to creat the m2m entry between Book and Author(s)
            BookEntity retrivedBookDetailsFromExternalEntity = retrivedBook.hitIsbnBookRequest(isbn);
            System.out.println(retrivedBook + "ceva");
            Iterator<Author> authorList = retrivedBookDetailsFromExternalEntity.getAuthors().listIterator();
            while (authorList.hasNext()) {
                System.out.println("Am ajuns la autori");
                checkAuthorUpdateOrAddDB(authorList.next());
            }

//            Inserting new book in to the database
            insertBookInDataBase(retrivedBookDetailsFromExternalEntity);

//            Insert m2m entry for book and authors(retrivedBookDetailsFromExternal, authorList)
            insertBookToAuthorsDBEntry(isbn, retrivedBookDetailsFromExternalEntity.getAuthors());


        }

    }


    //    Checks if a book is present in the database
    private boolean checkIfBookIsInDataBase(String isbn) throws SQLException {

        String sqlSeeIfBookIsPresent = "SELECT\n" +
                "books.isbn \n" +
                "FROM \n" +
                "books \n" +
                "WHERE \n" +
                "books.isbn = '"+isbn+"';";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlSeeIfBookIsPresent)) {

//            if present will execute method to update quantity by 1
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("We entered the try for isbn check");
                if (rs.isBeforeFirst()) {
                    System.out.println("We executed the querry " + isbn);
                    updateQuantityForExistingBook(isbn);
                    return true;
                } else {
                    System.out.println("We should not be here");
                    return false;
                }
            }
        }
    }


    //    If called method updates book quantity by 1
    private void updateQuantityForExistingBook(String isbnGiven) throws SQLException {

        String sqlUpdateQuantity = "UPDATE\n" +
                "books\n" +
                "SET\n" +
                "quantity = quantity+1\n" +
                "WHERE\n" +
                "books.isbn = '"+isbnGiven+"';";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdateQuantity)) {
            ps.executeUpdate(sqlUpdateQuantity);
        }
    }


    //    Veryfies checked author if is present in the database by matching both name and surname
//    On match updates the checked author id from the DB to be used in the users book entry flow
//    If not found sets a UUID and adds given author to the database
    private void checkAuthorUpdateOrAddDB(Author checkedAuthor) throws SQLException {

        String sqlCheckAuthorExistsUpdateUUID = "SELECT\n" +
                "authors.id\n" +
                "FROM\n" +
                "authors\n" +
                "WHERE\n" +
                "authors.name =  '"+checkedAuthor.getName()+"' AND \n" +
                "authors.surname = '"+checkedAuthor.getSurname()+"';";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlCheckAuthorExistsUpdateUUID)) {
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Got to try authors");
                while(rs.next()) {
                    if (rs.isBeforeFirst()) {
                        checkedAuthor.setId(UUID.fromString(rs.getString("id")));
                        System.out.println("Update author");
                    } else {
                        System.out.println("Will add author");
                        addAuthor(checkedAuthor);
                    }
                }
            }
        }
    }


    //    Method adds provided author to the database
    private void addAuthor(Author authorToAdd) throws SQLException {


        String sqlForInsertingNewAuthor = "INSERT INTO \n" +
                "bookOLX.authors(id, name, surname) \n" +
                "VALUES('"+authorToAdd.getId()+"' , '"+authorToAdd.getName()+"', '"+authorToAdd.getSurname()+"');";

        System.out.println("author to add" + authorToAdd);
        try (Connection con = JdbcConnection.getConnection()) {
            System.out.println("Entered try");
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewAuthor);
            ps.execute();
            System.out.println("Executed query to add");
            }

    }


    private void insertBookInDataBase(BookEntity bookEntityToInsert) throws SQLException {


//        bookToInsert.getIsbn(), bookToInsert.getTitle(), bookToInsert.getDescription()
        String sqlForInsertingNewBook =" INSERT INTO books \n" +
                "                (isbn,title,description,quantity) \n" +
                "                VALUES('"+ bookEntityToInsert.getIsbn()+"','"+ bookEntityToInsert.getTitle()+"','"+ bookEntityToInsert.getDescription()+"',1);";
        System.out.println(bookEntityToInsert.getIsbn());
        System.out.println(sqlForInsertingNewBook);
        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForInsertingNewBook)) {
            ps.execute();
        }
    }


    private void insertBookToAuthorsDBEntry(String isbn, List<Author> bookAuthors) throws SQLException {

        for (Author bookAuthor : bookAuthors) {

            String sqlQueryForLinkingBooksToAuthorsEntry =
                    "INSERT INTO \n" +
                            "author_book_mtm " +
                            "(id, book_isbn, author_id)" +
                            "VALUES( '" + UUID.randomUUID() + "' , '" + isbn + "' , '" + bookAuthor.getId() + "');";

            try (Connection con = JdbcConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sqlQueryForLinkingBooksToAuthorsEntry)) {
                ps.execute();
            }
        }
    }


    //    Adds BookMarket entry in DB
    private void addingMarketBookForUser(String providedIsbn, boolean rent, double priceToRent, boolean sell, double priceToSell, String recievedUserID, BookState providedBookState) throws SQLException {
        System.out.println(sell);
        System.out.println(rent);
        int tinySell = 0;
        int tinyRent = 0;
        if(sell){
            tinySell = 1;
        }
        if(rent){
            tinyRent =1;
        }
        String sqlQueryForBookMarketEntry;
        sqlQueryForBookMarketEntry = "INSERT INTO \n" +
                "book_market" +
                "(book_market_id, user_id, book_id, book_state, for_sell, sell_price, for_rent, rent_price, book_status) \n" +
                "VALUES('"+UUID.randomUUID()+"', '"+recievedUserID+"', '"+providedIsbn+"', '"+providedBookState+"', '"+tinySell+"', '"+priceToSell+"', '"+tinyRent+"', '"+priceToRent+"', '"+ BookStatus.AVAILABLE+"');";

        try (Connection con = JdbcConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQueryForBookMarketEntry)) {
            ps.execute();
        }
    }

//    Retrieve All Books from the Database

//    public List<BooksEntity> returnAllBooks(){
//        BooksRepository newRepo;
//        newRepo = new BooksRepository();
//        return newRepo.findAll();
//    }

}