package com.bookxchange.service;

import com.bookxchange.dto.MarketBookDto;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.MarketBook;
import com.bookxchange.model.Transaction;
import com.bookxchange.repositories.MarketBookRepo;
import com.bookxchange.repositories.MemberRepo;
import com.bookxchange.repositories.TransactionRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionService {
  TransactionRepo transactionRepo = new TransactionRepo();
  MarketBookRepo marketBookRepo = new MarketBookRepo();
  MemberRepo memberRepo = new MemberRepo();

  public static void main(String[] args) throws SQLException, IOException {
    TransactionService transactionService = new TransactionService();
    MarketBookRepo marketBookRepo = new MarketBookRepo();

    MarketBook marketBook1 =
        marketBookRepo.getMarketBook(UUID.fromString("1c821fb0-1024-4cd0-8f23-2d763fb2c13b"));
    MarketBook marketBook2 = marketBookRepo.getMarketBook(UUID.fromString("42a48524-20fd-4708-9311-55bf1a247eaf"));


    transactionService.rentBook(
        marketBook1, UUID.fromString("13177e99-14b5-43c5-a446-e0dc751c3153"));
    transactionService.rentBook( marketBook2, UUID.fromString("13177e99-14b5-43c5-a446-e0dc751c3153"));

  }


  public boolean rentBook(MarketBook marketBook, UUID memberIdTo) throws SQLException {
    if ( marketBook.getBookStatus().equals(BookStatus.AVAILABLE) && marketBook.isForRent()) {
      Transaction rentTransaction =
          new Transaction(
              marketBook.getId(), marketBook.getUserId(), memberIdTo, TransactionType.RENT);
      transactionRepo.createTransaction(rentTransaction);
      marketBookRepo.changeBookStatusInDb(marketBook);
      memberRepo.updatePointsToMember(marketBook);
      return true;
    }
    return false;
  }
}
