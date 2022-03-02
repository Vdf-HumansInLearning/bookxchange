package com.bookxchange.controller;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.RatingEntity;

import com.bookxchange.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/createBookRating")
    public ResponseEntity<RatingEntity> createBookRating(@RequestBody RatingEntity ratingEntity)  {
        try {
            ratingService.ratingABook(ratingEntity);

            return new ResponseEntity(ratingEntity, HttpStatus.CREATED);
        } catch (Exception invalidRatingException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidRatingException.getMessage());
        }
    }

    @PostMapping("/createMemberRating")
    public ResponseEntity<RatingEntity> createMemberRating(@RequestBody RatingEntity ratingEntity)  {

        try {
            ratingService.ratingAMember(ratingEntity);
            return new ResponseEntity(ratingEntity, HttpStatus.CREATED);
        } catch (Exception invalidRatingException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, invalidRatingException.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<RatingEntity>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {

        List<RatingEntity> list = ratingService.getAllRatings(pageNo,pageSize, sortBy);
        return new ResponseEntity<List<RatingEntity>>(list, HttpStatus.OK);
    }

}
