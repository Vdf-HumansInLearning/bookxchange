package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.*;
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
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookMarketRepository bookMarketRepository;

    public  void ratingAMember(RatingEntity ratingEntity) throws SQLException, IOException {

        System.out.println(ratingEntity.toString());
        if (ratingEntity.getUserId() == null) {
            throw new InvalidRatingException("User id can not be null when you rate a member");
        }

        if(ratingEntity.getUserId().equals(ratingEntity.getLeftBy())){
            throw new InvalidRatingException("Users can not let reviews to themselfs");
        }

        List<TransactionEntity> transaction = transactionRepository.getTransactionByWhoSelleddAndWhoBuys(ratingEntity.getLeftBy(), ratingEntity.getUserId());
        System.out.println(transaction.toString());
        if (transaction.isEmpty()) {
            throw new InvalidRatingException("These two users never interact");
        }
        ratingRepository.save(ratingEntity);

    }

    public  void ratingABook(RatingEntity ratingEntity) throws SQLException, IOException {

        if (ratingEntity.getBookId() == null) {
            throw new InvalidRatingException("Book id can not be null when you rate a book");
        }
        BookMarketEntity marketBook = bookMarketRepository.getBookMarketEntityByBookId(ratingEntity.getBookId());

        List<TransactionEntity> transaction = transactionRepository.getTransactionsByBookIdAndLeftBy(marketBook.getBookMarketId(), ratingEntity.getLeftBy());


        if (transaction.isEmpty()) {
            throw new InvalidRatingException("This user " + ratingEntity.getLeftBy() + " doesn't interact with this book");
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
