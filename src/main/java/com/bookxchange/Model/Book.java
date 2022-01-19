package com.bookxchange.Model;

import lombok.Data;

import java.util.List;

@Data
public class Book {

    private Integer isbn;
    private String title;
    private String description;
    private Boolean sellAvailability;
    private Boolean rentAvailability;
    private Integer quantity;
    private List<Author> author;

}
