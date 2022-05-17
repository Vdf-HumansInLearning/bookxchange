package com.bookxchange.mapper;

import com.bookxchange.dto.RatingDTO;
import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TransactionEntity toTransactionEntity(TransactionDTO transactionDto) {
        return new TransactionEntity(transactionDto.getMarketBookIdSupplier(), transactionDto.getMarketBookIdClient(), transactionDto.getSupplierId(),
                transactionDto.getClientId(), transactionDto.getTransactionType().toString());
    }

    public RatingEntity toRatingEntity(RatingDTO ratingDto) {

        return new RatingEntity(ratingDto.getGrade(), ratingDto.getDescription(), ratingDto.getLeftBy(), ratingDto.getUserId(), ratingDto.getBookId());
    }


}
