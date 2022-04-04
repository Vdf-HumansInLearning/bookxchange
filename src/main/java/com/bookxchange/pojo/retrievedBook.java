package com.bookxchange.pojo;

import com.bookxchange.model.BooksEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class retrievedBook {

    private boolean retrievedInfo = false;
    private BooksEntity retrievedBook = new BooksEntity();
    private String isbn;

    public retrievedBook(String isbn) {
        retrievedBook.setIsbn(isbn);
    }
}
