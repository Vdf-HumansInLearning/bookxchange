package com.bookxchange.dto;

import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import lombok.Data;

@Data
public class MarketBookDto {

  String bookTitle;
  String authorsName;
  String authorSurname;
  boolean forSell;
  Double sellPrice;
  boolean forRent;
  Double rentPrice;

  BookState bookState;
  BookStatus bookStatus;
  String memberUsername;

  public MarketBookDto(
      String bookTitle,
      String authorsName,
      String authorSurname,
      boolean forSell,
      Double sellPrice,
      boolean forRent,
      Double rentPrice,
      BookState bookState,
      BookStatus bookStatus,
      String memberUsername) {
    this.bookTitle = bookTitle;
    this.authorsName = authorsName;
    this.authorSurname = authorSurname;
    this.forSell = forSell;
    this.sellPrice = sellPrice;
    this.forRent = forRent;
    this.rentPrice = rentPrice;
    this.bookState = bookState;
    this.bookStatus = bookStatus;
    this.memberUsername = memberUsername;
  }


}
