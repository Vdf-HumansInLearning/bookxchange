package com.bookxchange.Model;

import com.bookxchange.Enums.BookState;
import lombok.Data;

import java.util.List;

@Data
public class UserBookLink {

    private long id;
    private long userId;
    private long bookId;
    private BookState bookState;
    private Boolean forSell;
    private double sellPrice;
    private Boolean forRent;
    private double rentPrice;
    private Boolean isRented;
    private Boolean isSold;
    private List<Long> membersIdThatRentedIt;


}
