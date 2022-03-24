package com.bookxchange.dto;

import com.bookxchange.model.*;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.pojo.Isbn;
import com.bookxchange.pojo.IsbnBasic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mapper {

    public TransactionEntity toTransaction(TransactionDto transactionDto) {
        return new TransactionEntity(transactionDto.getMarketBookId(), transactionDto.getMemberIdFrom(),
                transactionDto.getMemberIdTo(), transactionDto.getTransactionType(), transactionDto.getTransactionDate());
    }

    public RatingEntity toRatingEntity(RatingDto ratingDto ){

        return  new RatingEntity(ratingDto.getGrade(), ratingDto.getDescription(), ratingDto.getLeftBy(), ratingDto.getUserId(), ratingDto.getBookId());
    }

    public  RatingDto toRatingDto(RatingEntity ratingEntity ){
        return  new RatingDto(ratingEntity.getGrade(), ratingEntity.getDescription(), ratingEntity.getLeftByUuid(), ratingEntity.getUserIdUuid(), ratingEntity.getBookIsbn());
    }

    public BookMarketEntity toBookMarketEntity(BookMarketEntity recivedMarketBook) {
        return new BookMarketEntity(recivedMarketBook.getUserUuid(), recivedMarketBook.getBookIsbn(), recivedMarketBook.getBookState(), recivedMarketBook.getForSell(), recivedMarketBook.getSellPrice().doubleValue(), recivedMarketBook.getForRent(), recivedMarketBook.getRentPrice().doubleValue());
    }

    public BooksEntity toBooksEntity(BooksEntity receivedBook) {
        return  new BooksEntity(receivedBook.getIsbn(), receivedBook.getTitle(), receivedBook.getDescription(), 1, receivedBook.getAuthors());
    }

    public String toRetrieveIbn(String recievedJsonString) {
        Gson gson = new Gson();
        IsbnBasic returnedIsbn = gson.fromJson(recievedJsonString, IsbnBasic.class);
        System.out.println(returnedIsbn + " returned isbn");
        return returnedIsbn.getProvidedIsbn();
    }

    public BookListing toBookListing(String recivedListing) {
        Gson gson = new Gson();
        BookListing bookListingDTO = gson.fromJson(recivedListing, BookListing.class);
        return bookListingDTO;
    }


}
