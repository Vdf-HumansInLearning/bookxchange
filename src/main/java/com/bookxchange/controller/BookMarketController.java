package com.bookxchange.controller;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.service.BookMarketService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookMarket")
public class BookMarketController {

    private final BookMarketService bookMarketService;

    private static final Gson gson = new Gson();

    @Autowired
    public BookMarketController(BookMarketService bookMarketService) {
        this.bookMarketService = bookMarketService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("")
    public ResponseEntity<List<BookMarketEntity>> getAllByIsbn(@RequestParam String isbn) {

        List<BookMarketEntity> bookMarketEntities = bookMarketService.findAllByIsbn(isbn);

        return new ResponseEntity<>(bookMarketEntities, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<List<BookMarketEntity>> getAllByUserId(@RequestParam String userUuid) {

        List<BookMarketEntity> bookMarketEntities = bookMarketService.findAllByUserId(userUuid);

        return new ResponseEntity<>(bookMarketEntities, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarketEntityById(@PathVariable String id){
        bookMarketService.deleteBookMarketEntry(id);
        return new ResponseEntity<>(gson.toJson("Your entry has been successfully deleted"), HttpStatus.OK);
    }
}
