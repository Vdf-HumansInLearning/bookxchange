package com.bookxchange.controller;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.RatingDto;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    Mapper mapper = new Mapper();
    Logger logger = LoggerFactory.getLogger(RatingController.class);

//    books/{bookId}/rating
    @PostMapping("/createBookRating")
    public ResponseEntity<RatingEntity> createBookRating(@Valid @RequestBody RatingDto ratingDto) {

        logger.debug("createBookRating start ");

        RatingEntity ratingEntity = mapper.toRatingEntity(ratingDto);
        logger.debug("ratingEntity : ", ratingEntity);

            ratingService.ratingABook(ratingEntity);

            return new ResponseEntity(ratingEntity, HttpStatus.CREATED);

    }
//  members/{memberID}/rating
    @PostMapping("/createMemberRating")
    public ResponseEntity<RatingEntity> createMemberRating(@Valid @RequestBody RatingDto ratingDto) {

        logger.debug("createMemberRating start ");
        RatingEntity ratingEntity = mapper.toRatingEntity(ratingDto);
        logger.debug("ratingEntity : ", ratingEntity);

            ratingService.ratingAMember(ratingEntity);
            return new ResponseEntity(ratingEntity, HttpStatus.CREATED);

    }

//    la fel ca sus dar cu GET
    @GetMapping("/")
    public ResponseEntity<List<RatingEntity>> getAllRatings(@RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam(defaultValue = "ratingId") String sortBy,
                                                            @RequestParam(defaultValue = "true") boolean booksRating) {

        logger.debug("getAllRatings start ");

        List<RatingEntity> list = ratingService.getAllRatings(pageNo, pageSize, sortBy, booksRating);

        return new ResponseEntity<List<RatingEntity>>(list, HttpStatus.OK);
    }

}
