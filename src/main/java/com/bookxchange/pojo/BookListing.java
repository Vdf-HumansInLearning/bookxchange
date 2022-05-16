package com.bookxchange.pojo;

import com.bookxchange.model.BookEntity;
import com.bookxchange.model.BookMarketEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
//@Validated
public class BookListing {

    private boolean dataIsRetrievedDb;

    @Valid
    @NotNull
    private BookEntity receivedBook;


    @Valid
    @NotNull
    private BookMarketEntity receivedBookMarket;

}
