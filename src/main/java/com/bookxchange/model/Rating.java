package com.bookxchange.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Rating {

    private UUID ratingId;
    private int grade;
    private String description;
    private String leftBy;
    private String userID;
    private String bookID;

}
