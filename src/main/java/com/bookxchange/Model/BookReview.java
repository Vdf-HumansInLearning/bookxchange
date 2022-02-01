package com.bookxchange.Model;

import lombok.Data;

@Data
public class BookReview {

    private long id;
    private int rating;
    private String review;
    private long userId;
    private long isbn;
}
