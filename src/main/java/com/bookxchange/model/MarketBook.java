package com.bookxchange.model;

import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class MarketBook {

    private UUID id;
    private long userId;
    private long bookId;
    private BookState bookState;
    private boolean forSell;
    private Double sellPrice;
    private boolean forRent;
    private Double rentPrice;
    private BookStatus bookStatus;


    public MarketBook( long userId, long bookId, BookState bookState, boolean forSell, Double sellPrice, boolean forRent, Double rentPrice, BookStatus bookStatus) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.bookId = bookId;
        this.bookState = bookState;
        this.forSell = forSell;
        this.sellPrice = sellPrice;
        this.forRent = forRent;
        this.rentPrice = rentPrice;
        this.bookStatus = bookStatus;
    }

}
