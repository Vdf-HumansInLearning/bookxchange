package com.bookxchange.service;

import com.bookxchange.repositories.BookMarketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookMarketService {
    private final BookMarketRepository bookMarketRepository;

    public BookMarketService(BookMarketRepository bookMarketRepository) {
        this.bookMarketRepository = bookMarketRepository;
    }
    @Transactional
    public void updateBookMarketStatus(String status, String bookMarketID){
        bookMarketRepository.updateBookMarketStatusForId(status,bookMarketID);
    }
}
