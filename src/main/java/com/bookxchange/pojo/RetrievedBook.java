package com.bookxchange.pojo;

import com.bookxchange.dto.Mapper;
import com.bookxchange.model.BooksEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class RetrievedBook {

    private boolean retrievedInfo = false;
    private BooksEntity retrievedBook= new BooksEntity();
    private String isbn;



    public RetrievedBook(String isbn) {
        retrievedBook.setIsbn(isbn);
    }
}
