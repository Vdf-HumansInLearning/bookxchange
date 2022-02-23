package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.MarketBook;
import com.bookxchange.model.Transaction;
import com.bookxchange.repositories.MarketBookRepo;
import com.bookxchange.repositories.RatingRepo;
import com.bookxchange.repositories.TransactionRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;


public class RatingService {

    static TransactionRepo transactionRepo = new TransactionRepo();
    static RatingRepo ratingRepo = new RatingRepo();
    static MarketBookRepo marketBookRepo = new MarketBookRepo();

    public static void ratingAMember(int grade, String description, String leftBy, String userID) throws SQLException, IOException {

        if (userID == null) {
            throw new InvalidRatingException("User id can not be null when you rate a member");
        }

        Transaction transaction = transactionRepo.getTransactionByWhoSelleddAndWhoBuys(UUID.fromString(leftBy), UUID.fromString(userID));

        if (transaction == null || transaction.getId() == 0) {
            throw new InvalidRatingException("These two users never interact");
        } else {

            ratingRepo.addRating(grade, description, leftBy, userID, null);
        }
    }

    public static void ratingABook(int grade, String description, String leftBy, String bookID) throws SQLException, IOException {

        MarketBook marketBook = marketBookRepo.getMarketBook(UUID.fromString(bookID));

        if (bookID == null) {
            throw new InvalidRatingException("Book id can not be null when you rate a book");
        }
        Transaction transaction = transactionRepo.getTransactionByBookIdAndLeftBy(UUID.fromString(bookID), UUID.fromString(leftBy));
        if (transaction == null || transaction.getId() == 0) {
            throw new InvalidRatingException("This user" + leftBy + "doesn't interact with this book");
        } else {
            ratingRepo.addRating(grade, description, leftBy, null, marketBook.getBookId());
        }
    }


    public static void main(String[] args) throws SQLException, IOException {
        ratingABook(2, "ceva", "13177e99-14b5-43c5-a446-e0dc751c3153", "1c821fb0-1024-4cd0-8f23-2d763fb2c13b");
        // ratingAMember(3,"carte buna", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9","13177e99-14b5-43c5-a446-e0dc751c3153");
        // ratingAMember(3,"carte buna", "ae677979-ffec-4a90-a3e5-a5d1d31c0ee9","13177e99-14b5-43c5-a446-e0dc751c3153");

    }


}
