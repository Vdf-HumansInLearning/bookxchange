package com.bookxchange.dto;

import com.bookxchange.model.RatingEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public  RatingEntity toRatingEntity(RatingDto ratingDto ){

        return  new RatingEntity(ratingDto.getGrade(), ratingDto.getDescription(), ratingDto.getLeftBy(), ratingDto.getUserId(), ratingDto.getBookId());
    }

    public  RatingDto toRatingDto(RatingEntity ratingEntity ){
        return  new RatingDto(ratingEntity.getGrade(), ratingEntity.getDescription(), ratingEntity.getLeftBy(), ratingEntity.getUserId(), ratingEntity.getBookId());
    }
}
