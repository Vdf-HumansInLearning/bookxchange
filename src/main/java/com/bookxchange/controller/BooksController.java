package com.bookxchange.controller;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.dto.Mapper;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.security.JwtTokenUtil;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;

import com.bookxchange.service.IsbnService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    private final JwtTokenUtil jwtTokenUtil;


    Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    public BooksController(BookService workingBookService, BookMarketService workingBookMarketService, JwtTokenUtil jwtTokenUtil) {
        this.workingBookService = workingBookService;
        this.workingBookMarketService = workingBookMarketService;

        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping("/getBookDetailsISBN")
    public ResponseEntity<RetrievedBook> retrieveBookDetails(@RequestParam String providedIsbn) {

        logger.debug("RetriveBookDetails starts : ");

        RetrievedBook retrievedBookToReturn = new RetrievedBook(providedIsbn);
        retrievedBookToReturn.setRetrievedInfo(false);

        logger.debug("Starts to do the search  : " );

        BooksEntity bookDetails = workingBookService.retrieveBookFromDB(retrievedBookToReturn.getRetrievedBook().getIsbn());
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
    public ResponseEntity<BookListing> creatBookEntry(@RequestHeader HttpHeaders headers, @RequestBody BookListing receivedBookInfo) {


        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Claims claims=  jwtTokenUtil.getAllClaimsFromToken(token.substring(7));

       receivedBookInfo.getReceivedBookMarket().setUserUuid(claims.get("userUUID").toString());

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
