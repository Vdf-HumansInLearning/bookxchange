package com.bookxchange.service;

import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.model.RatingEntity;
import com.bookxchange.repositories.BookMarketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addBookMarketEntry(BookMarketEntity bookMarketEntityToAdd){
        bookMarketRepository.save(bookMarketEntityToAdd);
    }

 public List<BookMarketEntity>  findAllByIsbn( String isbn){

        return  bookMarketRepository.findAllByBookIsbn(isbn);
 }
    public List<BookMarketEntity>  findAllByUserId( String userUuid){

        return  bookMarketRepository.findAllByUserUuid(userUuid);
    }
}
