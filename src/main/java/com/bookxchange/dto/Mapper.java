package com.bookxchange.dto;

import com.bookxchange.model.*;
import com.bookxchange.pojo.BookListing;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TransactionEntity toTransactionEntity(TransactionDto transactionDto) {
        return new TransactionEntity(transactionDto.getMarketBookId(), transactionDto.getSupplier(),
                transactionDto.getClient(), transactionDto.getTransactionType().toString());
    }

    public RatingEntity toRatingEntity(RatingDto ratingDto ){

        return  new RatingEntity(ratingDto.getGrade(), ratingDto.getDescription(), ratingDto.getLeftBy(), ratingDto.getUserId(), ratingDto.getBookId());
    }

    public  RatingDto toRatingDto(RatingEntity ratingEntity ){
        return  new RatingDto(ratingEntity.getGrade(), ratingEntity.getDescription(), ratingEntity.getLeftByUuid(), ratingEntity.getUserIdUuid(), ratingEntity.getBookIsbn());
    }

    public BookMarketEntity toBookMarketEntity(BookMarketEntity recivedMarketBook) {
        return new BookMarketEntity(recivedMarketBook);
    }

    public BooksEntity toBooksEntity(BooksEntity receivedBook) {
        return  new BooksEntity(receivedBook.getIsbn(), receivedBook.getTitle(), receivedBook.getDescription(), 1, receivedBook.getAuthors());
    }


    public BookListing toBookListing(String recivedListing) {
        Gson gson = new Gson();
        BookListing bookListingDTO = gson.fromJson(recivedListing, BookListing.class);
        return bookListingDTO;
    }


}
