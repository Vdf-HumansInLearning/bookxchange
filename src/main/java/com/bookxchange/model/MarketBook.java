package com.bookxchange.model;

import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import lombok.Data;

@Data
public class MarketBook {

    private long id;
    private long userId;
    private long bookId;
    private BookState bookState;
    private boolean forSell;
    private Double sellPrice;
    private boolean forRent;
    private Double rentPrice;
    private BookStatus bookStatus;

}
