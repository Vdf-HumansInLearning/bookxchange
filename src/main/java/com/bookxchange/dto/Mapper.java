package com.bookxchange.dto;

import com.bookxchange.model.MarketBook;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
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

    public MarketBook toMarketBook(MarketBook recivedMarketBook) {
        return new MarketBook(recivedMarketBook.getUserId(), recivedMarketBook.getBookId().toString(), recivedMarketBook.getBookState(), recivedMarketBook.isForSell(), recivedMarketBook.getSellPrice().doubleValue(), recivedMarketBook.isForRent(), recivedMarketBook.getRentPrice().doubleValue());
    }



}
