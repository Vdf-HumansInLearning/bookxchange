package com.bookxchange.model;

import com.bookxchange.enums.BookState;
import com.bookxchange.enums.BookStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Setter
@NoArgsConstructor
public class MarketBook {

  private UUID id;
  private UUID userId;
  private String bookId;
  private BookState bookState;
  private boolean forSell;
  private Double sellPrice;
  private boolean forRent;
  private Double rentPrice;
  private BookStatus bookStatus;

  public MarketBook(
          UUID userId,
          String bookId,
      BookState bookState,
      boolean forSell,
      Double sellPrice,
      boolean forRent,
      Double rentPrice
      ) {
    this.id = UUID.randomUUID();
    this.userId = userId;
    this.bookId = bookId;
    this.bookState = bookState;
    this.forSell = forSell;
    this.sellPrice = sellPrice;
    this.forRent = forRent;
    this.rentPrice = rentPrice;
    this.bookStatus = BookStatus.AVAILABLE;
  }

}
