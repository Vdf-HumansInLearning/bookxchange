package com.bookxchange.service;

import com.bookxchange.exception.InvalidRatingException;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.repository.BookMarketRepository;
import com.bookxchange.repository.RatingRepository;
import com.bookxchange.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RatingService {


    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookMarketRepository bookMarketRepository;

    public void ratingAMember(RatingEntity ratingEntity) {

        if (ratingEntity.getUserIdUuid() == null || ratingEntity.getLeftByUuid() == null) {
            throw new InvalidRatingException("User id can not be null when you rate a member");
        }

        if (ratingEntity.getUserIdUuid().equals(ratingEntity.getLeftByUuid())) {
            throw new InvalidRatingException("Users can not let reviews to themselves");
        }

        System.out.println(ratingEntity + " Totul TOTT");

        List<TransactionEntity> transaction = transactionRepository.findTransactionEntitiesByMemberuuIdToAndMemberuuIdFrom(ratingEntity.getLeftByUuid(), ratingEntity.getUserIdUuid());

        if (transaction.isEmpty()) {
            throw new InvalidRatingException("These two users never interact");
        }
        ratingRepository.save(ratingEntity);

    }

    public void ratingABook(RatingEntity ratingEntity) {

       if(!ApplicationUtils.checkGrade(ratingEntity.getGrade())) {
           throw new InvalidRatingException("Grade should be digits between 0 and 5");
       }


        if (ratingEntity.getBookIsbn() == null) {
            throw new InvalidRatingException("Book id can not be null when you rate a book");
        }
        BookMarketEntity marketBook = bookMarketRepository.getBookMarketEntityByBookId(ratingEntity.getBookIsbn());

        if (marketBook == null) {
            throw new InvalidRatingException("This user " + ratingEntity.getLeftByUuid() + " doesn't interact with this book");
        }
        List<TransactionEntity> transaction = transactionRepository.getTransactionsByBookIdAndLeftBy(marketBook.getBookMarketUuid(), ratingEntity.getLeftByUuid());
        if (transaction.isEmpty()) {
            throw new InvalidRatingException("This user " + ratingEntity.getLeftByUuid() + " doesn't interact with this book");
        }
        ratingRepository.save(ratingEntity);

    }

    public List<RatingEntity> getAllRatings(Integer pageNo, Integer pageSize, String sortBy, boolean booksRating) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<RatingEntity> pagedResult = ratingRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            if (booksRating) {
                return pagedResult.getContent().stream().filter(ratingEntity -> ratingEntity.getBookIsbn() != null).collect(Collectors.toList());
            } else {
                return pagedResult.getContent().stream().filter(ratingEntity -> ratingEntity.getBookIsbn() == null).collect(Collectors.toList());
            }
        } else {
            return new ArrayList<>();
        }
    }

}
