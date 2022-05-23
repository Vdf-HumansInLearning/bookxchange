package com.bookxchange.pojo;

import com.bookxchange.model.BookEntity;
import com.bookxchange.model.BookMarketEntity;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class BookListing {

    private boolean dataIsRetrievedDb;

    @Valid
    @Nullable
    private BookEntity receivedBook;


    @Valid
    @NotNull(message = "receivedBookMarket nulll")
    private BookMarketEntity receivedBookMarket;

}
