package com.bookxchange.service;

import com.bookxchange.dto.MarketBookDto;
import com.bookxchange.enums.BookStatus;
import com.bookxchange.repositories.MarketBookRepo;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class MarketPlaceService {

  public static void main(String[] args) throws SQLException {

    MarketBookRepo marketBookRepo = new MarketBookRepo();

    //    List<MarketBookDto> lsmarket = marketBookRepo.getAllMarketBook();
    //
    //        for (MarketBookDto mbd:lsmarket
    //             ) {
    //            System.out.println(mbd);
    //        }

    //            List<MarketBookDto> lsmarket =
    // marketBookRepo.getAllMarketBookByBookStatus(BookStatus.AVAILABLE);
    //
    //        for (MarketBookDto mbd:lsmarket
    //             ) {
    //            System.out.println(mbd);
    //        }

    System.out.println(
        marketBookRepo.getAllMarketBookById(
            UUID.fromString("102126b8-49a9-4eb3-bc4f-424d89a56f8b")));
  }
}
