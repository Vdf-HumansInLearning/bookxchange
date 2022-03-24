package com.bookxchange.pojo;

import com.bookxchange.dto.Mapper;
import com.bookxchange.model.BooksEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class RetrievedBook {

    private boolean retrievedInfo = false;
    private BooksEntity retrievedBook;
    private final Mapper mapIsbn = new Mapper();

    public RetrievedBook(String providedIsbn) {
        retrievedBook = new BooksEntity();
        retrievedBook.setIsbn(mapIsbn.toRetrieveIbn(providedIsbn));

    }
}
