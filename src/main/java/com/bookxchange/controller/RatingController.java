package com.bookxchange.controller;

import com.bookxchange.dto.RatingDTO;
import com.bookxchange.mapper.Mapper;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.service.ApplicationUtils;
import com.bookxchange.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    RatingService ratingService;
    Mapper mapper = new Mapper();
    Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @PostMapping("/book")
    public ResponseEntity<RatingEntity> createBookRating(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody RatingDTO ratingDto) {

        logger.debug("createBookRating start ");
        ratingDto.setLeftBy(ApplicationUtils.getUserFromToken(token));
        RatingEntity ratingEntity = mapper.toRatingEntity(ratingDto);
        logger.debug("ratingEntity : ", ratingEntity);

        ratingService.ratingABook(ratingEntity);

        return new ResponseEntity<>(ratingEntity, HttpStatus.CREATED);

    }


    @PostMapping("/member")
    public ResponseEntity<RatingEntity> createMemberRating(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody RatingDTO ratingDto) {

        logger.debug("createMemberRating start ");
        ratingDto.setLeftBy(ApplicationUtils.getUserFromToken(token));
        RatingEntity ratingEntity = mapper.toRatingEntity(ratingDto);
        logger.debug("ratingEntity : ", ratingEntity);

        ratingService.ratingAMember(ratingEntity);
        return new ResponseEntity<>(ratingEntity, HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<List<RatingEntity>> getAllRatings(@RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam(defaultValue = "ratingId") String sortBy,
                                                            @RequestParam(defaultValue = "true") boolean booksRating) {

        logger.debug("getAllRatings start ");

        List<RatingEntity> list = ratingService.getAllRatings(pageNo, pageSize, sortBy, booksRating);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
