package com.bookxchange.model;

import com.bookxchange.enums.TransactionType;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
@Data
public class Transaction {

  private long id;
  private UUID marketBookId;
  private UUID memberIdFrom;
  private UUID memberIdTo;
  private TransactionType transactionType;
  private LocalDate transactionDate;
  private LocalDate expectedReturnDate;

  public Transaction(
          UUID marketBookId,
          UUID memberIdFrom,
          UUID memberIdTo,
      TransactionType transactionType) {
    this.marketBookId = marketBookId;
    this.memberIdFrom = memberIdFrom;
    this.memberIdTo = memberIdTo;
    this.transactionType = transactionType;
    this.transactionDate = LocalDate.now();
    if(transactionType.equals(TransactionType.RENT)){
      this.expectedReturnDate = transactionDate.plusDays(30);
    }

  }

  public Transaction() {

  }
}
