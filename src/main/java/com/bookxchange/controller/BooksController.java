package com.bookxchange.controller;

import com.bookxchange.dto.Mapper;
import com.bookxchange.enums.BookState;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.BooksEntity;
import com.bookxchange.model.MarketBook;
import com.bookxchange.repositories.BookRepo;
import com.bookxchange.service.BookService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("books")

public class BooksController {

    private final Mapper mapper = new Mapper();
    private final BookService workingBookService;

    @Autowired
    public BooksController(BookService workingBookService)  {
    this.workingBookService = workingBookService;
    }


    @PostMapping ("/userAddBook")
//    public ResponseEntity<BooksEntity> creatANewBook(@RequestBody String providedIsbn, boolean rent, String priceToRent , boolean sell, String priceToSell, String recievedUserID, BookState providedBookState) {
            public ResponseEntity<MarketBook> creatBookEntry(@RequestBody MarketBook marketBookData) {
        MarketBook marketBookEntity = mapper.toMarketBook(marketBookData);

        try {
            System.out.println(marketBookEntity.getBookId() + " " + marketBookEntity.isForRent());
            System.out.println(marketBookEntity.getRentPrice());
            workingBookService.userAddsNewBook(marketBookEntity);

            return new ResponseEntity(marketBookEntity, HttpStatus.CREATED);
        } catch (Exception invalidISBNException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidISBNException.getMessage());

        }
    }

//    @GetMapping("/listOfBooks")
//    public ResponseEntity<BooksEntity[]> getAllBooks() {
//
//        try{
//
//        }
//
//    }

}
