package com.bookxchange.controller;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.dto.Mapper;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;

import com.bookxchange.service.IsbnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("books")

public class BooksController {

    private final Mapper mapper = new Mapper();
    private final BookService workingBookService;
    private final BookMarketService workingBookMarketService;
    private final IsbnService workingIsbnService = new IsbnService();

    @Autowired
    public BooksController(BookService workingBookService, BookMarketService workingBookMarketService) {
        this.workingBookService = workingBookService;
        this.workingBookMarketService = workingBookMarketService;
    }


    @GetMapping("/getBookDetailsISBN")
    public ResponseEntity<RetrievedBook> RetriveBookDetails(@RequestBody String providedIsbn) {
        RetrievedBook retrievedBookToReturn = new RetrievedBook(providedIsbn);
        retrievedBookToReturn.setRetrievedInfo(false);
        System.out.println("Starts to do the search");
        System.out.println(providedIsbn);
        System.out.println(retrievedBookToReturn.getRetrievedBook().getIsbn());
        BooksEntity bookDetails = workingBookService.retriveBookFromDB(retrievedBookToReturn.getRetrievedBook().getIsbn());
        System.out.println("Has finished the search " + bookDetails);
        if (bookDetails != null) {
            retrievedBookToReturn.setRetrievedBook(bookDetails);
            retrievedBookToReturn.setRetrievedInfo(true);
        } else {
            bookDetails = workingIsbnService.hitIsbnBookRequest(retrievedBookToReturn.getRetrievedBook().getIsbn());

            retrievedBookToReturn.setRetrievedBook(bookDetails);
            System.out.println(retrievedBookToReturn.getRetrievedBook().getTitle() + " AM GASIT TITLUL");
            System.out.println("From the retrun package " + retrievedBookToReturn.getRetrievedBook().getTitle());
        }

        try {
            if (retrievedBookToReturn.getRetrievedBook() != null) {
                return new ResponseEntity(retrievedBookToReturn, HttpStatus.FOUND);

            } else {
                throw new ResponseStatusException(
                        HttpStatus.NO_CONTENT, new BooksExceptions("No content found, please add manually").toString());
            }
        } catch (Exception bookExceptions) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, bookExceptions.getMessage());

        }
    }



//    recivive ISBN from json

//    verify DB -

    @PostMapping ("/userAddBook")
//    public ResponseEntity<BooksEntity> creatANewBook(@RequestBody String providedIsbn, boolean rent, String priceToRent , boolean sell, String priceToSell, String recievedUserID, BookState providedBookState) {
    public ResponseEntity<BookListing> creatBookEntry(@RequestBody String receivedBookListing) {

        BookListing receivedBookInfo = mapper.toBookListing(receivedBookListing);


        try {

            if(receivedBookInfo.isDataIsRetrievedDb()) {
                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
            } else {
                workingBookService.userAddsNewBook(receivedBookInfo.getReceivedBook());
                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
            }


//            System.out.println(marketBookEntity.getBookIsbn() + " aici " + marketBookEntity.getForRent());
//            System.out.println(marketBookEntity.getRentPrice());
//            workingBookService.userAddsNewBook(marketBookEntity.getBookIsbn());
//            workingBookMarketService.addBookMarketEntry(marketBookEntity);

            return new ResponseEntity(receivedBookInfo, HttpStatus.CREATED);
        } catch (Exception invalidISBNException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidISBNException.getMessage());

        }
    }


//    @PostMapping ("/userAddBook1")
////    public ResponseEntity<BooksEntity> creatANewBook(@RequestBody String providedIsbn, boolean rent, String priceToRent , boolean sell, String priceToSell, String recievedUserID, BookState providedBookState) {
//            public ResponseEntity<BookMarketEntity> creatBookEntry1(@RequestBody BookMarketEntity marketBookData) {
//        BookMarketEntity marketBookEntity = mapper.toBookMarketEntity(marketBookData);
//        marketBookEntity.setBookMarketUuid(UUID.randomUUID().toString());
//        BooksEntity recievedBookdDetails = mapper.toBooksEntity(marketBookEntity.getBookIsbn().);
//
//        try {
//            System.out.println(marketBookEntity.getBookIsbn() + " aici " + marketBookEntity.getForRent());
//            System.out.println(marketBookEntity.getRentPrice());
//            workingBookService.userAddsNewBook(marketBookEntity.getBookIsbn());
//            workingBookMarketService.addBookMarketEntry(marketBookEntity);
//
//            return new ResponseEntity(marketBookEntity, HttpStatus.CREATED);
//        } catch (Exception invalidISBNException) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, invalidISBNException.getMessage());
//
//        }
//    }

//    @GetMapping("/listOfBooks")
//    public ResponseEntity<BooksEntity[]> getAllBooks() {
//
//        try{
//
//        }
//
//    }

}
