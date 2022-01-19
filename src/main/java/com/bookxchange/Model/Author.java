package com.bookxchange.Model;

import lombok.Data;

import java.util.List;

@Data
public class Author {

    private Integer id;
    private String name;
    private String surname;
    private long birthDate;
    private List<Book> books;

}
