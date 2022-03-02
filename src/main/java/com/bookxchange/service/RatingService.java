package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.MarketBook;
import com.bookxchange.model.Rating;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.Transaction;
import com.bookxchange.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RatingService {

    static TransactionRepo transactionRepo = new TransactionRepo();
    static MarketBookRepo marketBookRepo = new MarketBookRepo();

    @Autowired
    RatingRepository ratingRepository;

    public  void ratingAMember(RatingEntity ratingEntity) throws SQLException, IOException {

        if (ratingEntity.getUserId() == null) {
            throw new InvalidRatingException("User id can not be null when you rate a member");
        }

        Transaction transaction = transactionRepo.getTransactionByWhoSelleddAndWhoBuys(UUID.fromString(ratingEntity.getLeftBy()), UUID.fromString(ratingEntity.getUserId()));

        if (transaction == null ) {
            throw new InvalidRatingException("These two users never interact");
        }
        ratingRepository.save(ratingEntity);

    }

    public  void ratingABook(RatingEntity ratingEntity) throws SQLException, IOException {

        MarketBook marketBook = marketBookRepo.getMarketBook(UUID.fromString(ratingEntity.getBookId() ));

        if (ratingEntity.getBookId() == null) {
            throw new InvalidRatingException("Book id can not be null when you rate a book");
        }

        Transaction transaction = transactionRepo.getTransactionByBookIdAndLeftBy(UUID.fromString(ratingEntity.getBookId() ), UUID.fromString(ratingEntity.getLeftBy()));

        if (transaction == null ) {
            throw new InvalidRatingException("This user" + ratingEntity.getLeftBy() + "doesn't interact with this book");
        }

        ratingRepository.save(ratingEntity);


    }

    public List<RatingEntity> getAllRatings(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<RatingEntity> pagedResult = ratingRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<RatingEntity>();
        }
    }

}
