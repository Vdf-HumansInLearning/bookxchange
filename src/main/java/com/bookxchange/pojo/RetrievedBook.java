package com.bookxchange.pojo;

import com.bookxchange.model.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class RetrievedBook {

    private boolean retrievedInfo = false;
    private BookEntity retrievedBook = new BookEntity();
    private String isbn;

    public RetrievedBook(String isbn) {
        retrievedBook.setIsbn(isbn);
    }
}
