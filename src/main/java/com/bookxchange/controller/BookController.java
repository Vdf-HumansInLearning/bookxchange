package com.bookxchange.controller;

import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.service.ApplicationUtils;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("books")

public class BookController {

    private final BookService workingBookService;
    private final BookMarketService workingBookMarketService;



    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService workingBookService, BookMarketService workingBookMarketService) {
        this.workingBookService = workingBookService;
        this.workingBookMarketService = workingBookMarketService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/isbn")
    public ResponseEntity<RetrievedBook> retrieveBookDetails(@Valid @RequestParam String isbn) {

        String methodName = "[retrieveBookDetails]";
        logger.info("Entering {}", methodName);

        RetrievedBook retrievedBookData = workingBookService.checkDbOrAttemptToPopulateFromIsbn(isbn);
        return new ResponseEntity<>(retrievedBookData , HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<String> creatBookEntry(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody BookListing receivedBookInfo) {

        String methodName = "[creatBookEntry]";
        logger.info("Entering {}", methodName);

        receivedBookInfo.getReceivedBookMarket().setUserUuid(ApplicationUtils.getUserFromToken(token));
        String responseMessage = workingBookMarketService.addBookMarketEntityAndBookIfCustom(receivedBookInfo);

        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }


}

