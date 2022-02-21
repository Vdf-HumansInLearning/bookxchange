package com.bookxchange.service;

import com.bookxchange.dto.BookDto;
import com.bookxchange.repositories.BookRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookService {

  public static void main(String[] args) throws SQLException, IOException {

    BookRepo bookRepo = new BookRepo();
    List<BookDto> allBooksEver = bookRepo.getAllBooksEver();

    for (BookDto book : allBooksEver) {
      System.out.println(book);
    }
  }
}
