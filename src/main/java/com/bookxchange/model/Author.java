package com.bookxchange.model;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Author {

  private long id;
  private String name;
  private String surname;
  private Date birthDate;
  private List<Book> books;
}
