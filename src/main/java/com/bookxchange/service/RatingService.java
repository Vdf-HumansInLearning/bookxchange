package com.bookxchange.service;

import com.bookxchange.customExceptions.InvalidRatingException;
import com.bookxchange.model.Transaction;
import com.bookxchange.repositories.RatingRepo;

import java.sql.SQLException;

public class RatingService {


    public static void ratingAMember(int grade, String description, String leftBy, String userID) throws SQLException {

        if (userID == null) {
            throw new InvalidRatingException("User id can not be null when you rate a member");
        }

        //TODO
        //aici fa get la tranzactie pe baza leftby si userid
        Transaction transaction = new Transaction();

        if(transaction==null){
            throw new InvalidRatingException("These two users never interact");
        }

        RatingRepo rp = new RatingRepo();
        rp.addRating(1, "carte membru", "eu am lasat", userID, null);
    }

    public static void ratingABook(int grade, String description, String leftBy, String bookID) throws SQLException {

        if (bookID == null) {
            throw new InvalidRatingException("Book id can not be null when you rate a member");
        }

        //TODO
        //aici fa get la tranzactie pe baza leftby si userid
        Transaction transaction = new Transaction();

        if(transaction==null){
            throw new InvalidRatingException("This user" + leftBy + "doesn't interact with this book");
        }

        RatingRepo rp = new RatingRepo();
        rp.addRating(1, "carte membru", "eu am lasat", null, bookID);
    }



}
