package com.bookxchange.controller;

import com.bookxchange.exception.BooksExceptions;
import com.bookxchange.model.BookEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.service.ApplicationUtils;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;
import com.bookxchange.service.IsbnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.SQLOutput;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("books")

public class BookController {

    //    private final Mapper mapper = new Mapper();
    private final BookService workingBookService;
    private final BookMarketService workingBookMarketService;
    private final IsbnService workingIsbnService;

    private final JwtTokenUtil jwtTokenUtil;


    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService workingBookService, BookMarketService workingBookMarketService, IsbnService workingIsbnService, JwtTokenUtil jwtTokenUtil) {
        this.workingBookService = workingBookService;
        this.workingBookMarketService = workingBookMarketService;
        this.workingIsbnService = workingIsbnService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getBookDetailsISBN")
    public ResponseEntity<RetrievedBook> RetriveBookDetails(
            @Valid @RequestParam String providedIsbn) {

        logger.debug("RetriveBookDetails starts : ");

        RetrievedBook retrievedBookToReturn = new RetrievedBook(providedIsbn);
        retrievedBookToReturn.setRetrievedInfo(false);
        retrievedBookToReturn.setIsbn(providedIsbn);

        logger.debug("Starts to do the search  : ");


        BookEntity bookDetails = workingBookService.checkDbOrAttemptToPopulateFromIsbn(providedIsbn);
        retrievedBookToReturn.setRetrievedBook(bookDetails);



            if (retrievedBookToReturn.getRetrievedBook() != null) {
                retrievedBookToReturn.setRetrievedInfo(true);
                return new ResponseEntity<>(retrievedBookToReturn, HttpStatus.OK);
            } else {
                return  new ResponseEntity<> (retrievedBookToReturn, HttpStatus.OK);
            }


    }

    @Transactional
    @PostMapping("/userAddBook")
    public ResponseEntity<BookListing> creatBookEntry(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody BookListing receivedBookInfo) {

        receivedBookInfo.getReceivedBookMarket().setUserUuid(ApplicationUtils.getUserFromToken(token));
        System.out.println(receivedBookInfo.getReceivedBookMarket().getUserUuid() + " E NULL?");

        try {
            if (receivedBookInfo.isDataIsRetrievedDb()) {
                System.out.println("Reached adding book in market place");
                System.out.println("reached here?");
                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
            } else {
                workingBookService.addNewBookToDB(receivedBookInfo.getReceivedBook());
                workingBookMarketService.addBookMarketEntry(receivedBookInfo.getReceivedBookMarket());
                workingBookService.updateQuantityAtAdding(receivedBookInfo.getReceivedBook().getIsbn());
            }

            return new ResponseEntity<>(receivedBookInfo, HttpStatus.CREATED);
        } catch (Exception invalidISBNException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidISBNException.getMessage());

        }
    }

}

