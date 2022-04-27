package com.bookxchange.pojo;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.BooksEntity;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
//@Validated
public class BookListing {

    private boolean dataIsRetrievedDb;

    @Valid
    @NotNull
    private BooksEntity receivedBook;


    @Valid
    @NotNull
    private BookMarketEntity receivedBookMarket;

}
