package com.bookxchange.controller;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.dto.Mapper;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;

import com.bookxchange.service.IsbnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("books")

public class BooksController {

    private final Mapper mapper = new Mapper();
    private final BookService workingBookService;
    private final BookMarketService workingBookMarketService;
    private final IsbnService workingIsbnService = new IsbnService();

    Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    public BooksController(BookService workingBookService, BookMarketService workingBookMarketService) {
        this.workingBookService = workingBookService;
        this.workingBookMarketService = workingBookMarketService;
    }


    @GetMapping("/getBookDetailsISBN")
    public ResponseEntity<RetrievedBook> RetriveBookDetails(@RequestParam String providedIsbn) {

        logger.debug("RetriveBookDetails starts : ");

        RetrievedBook retrievedBookToReturn = new RetrievedBook(providedIsbn);
        retrievedBookToReturn.setRetrievedInfo(false);

        System.out.println(retrievedBookToReturn);
        logger.debug("Starts to do the search  : " );

        BooksEntity bookDetails = workingBookService.retriveBookFromDB(retrievedBookToReturn.getRetrievedBook().getIsbn());
        logger.debug("Has finished the search : " + bookDetails);

        if (bookDetails != null) {
            retrievedBookToReturn.setRetrievedBook(bookDetails);
            retrievedBookToReturn.setRetrievedInfo(true);
        } else {
            bookDetails = workingIsbnService.hitIsbnBookRequest(retrievedBookToReturn.getRetrievedBook().getIsbn());
            retrievedBookToReturn.setRetrievedBook(bookDetails);
            logger.debug("From the retrun package " + retrievedBookToReturn.getRetrievedBook().getTitle());
        }

        try {
            if (retrievedBookToReturn.getRetrievedBook() != null) {
                return new ResponseEntity(retrievedBookToReturn, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NO_CONTENT, new BooksExceptions("No content found, please add manually").toString());
            }
        } catch (Exception bookExceptions) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, bookExceptions.getMessage());
        }
    }


    @PostMapping ("/userAddBook")
    public ResponseEntity<BookListing> creatBookEntry(@RequestBody BookListing receivedBookInfo) {


        try {
            if(receivedBookInfo.isDataIsRetrievedDb()) {

                workingBookService.updateQuantityAtAdding(receivedBookInfo.getReceivedBook().getIsbn());

                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
            } else {

                workingBookService.userAddsNewBook(receivedBookInfo.getReceivedBook());
                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
            }

            return new ResponseEntity(receivedBookInfo, HttpStatus.CREATED);
        } catch (Exception invalidISBNException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidISBNException.getMessage());

        }
    }


}
