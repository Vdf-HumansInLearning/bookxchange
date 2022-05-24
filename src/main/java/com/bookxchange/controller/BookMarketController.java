package com.bookxchange.controller;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.service.BookMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookMarket")
public class BookMarketController {

    BookMarketService bookMarketService;

    @Autowired
    public BookMarketController(BookMarketService bookMarketService) {
        this.bookMarketService = bookMarketService;
    }


    @GetMapping("")
    public ResponseEntity<List<BookMarketEntity>> getAllByIsbn(@RequestParam String isbn) {

        List<BookMarketEntity> bookMarketEntities = bookMarketService.findAllByIsbn(isbn);

        return new ResponseEntity<>(bookMarketEntities, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<BookMarketEntity>> getAllByUserId(@RequestParam String userUuid) {

        List<BookMarketEntity> bookMarketEntities = bookMarketService.findAllByUserId(userUuid);

        return new ResponseEntity<>(bookMarketEntities, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarketEntityById(@PathVariable String id){
        bookMarketService.deleteBookMarketEntry(id);
        return new ResponseEntity<>("Book market entry was deleted", HttpStatus.OK);
    }
}
