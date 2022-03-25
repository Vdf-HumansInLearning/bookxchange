package com.bookxchange.pojo;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.BooksEntity;
import lombok.Data;

@Data
public class BookListing {

    private boolean dataIsRetrievedDb;
    private BooksEntity receivedBook;
    private BookMarketEntity receivedBookMarket;

}
