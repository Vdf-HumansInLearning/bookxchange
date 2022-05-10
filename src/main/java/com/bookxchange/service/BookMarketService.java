package com.bookxchange.service;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.repositories.BookMarketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookMarketService {
    private final BookMarketRepository bookMarketRepository;

    public BookMarketService(BookMarketRepository bookMarketRepository) {
        this.bookMarketRepository = bookMarketRepository;
    }

    @Transactional
    public void updateBookMarketStatus(String status, String bookMarketID) {
        bookMarketRepository.updateBookMarketStatusForId(status, bookMarketID);
    }

    public void addBookMarketEntry(BookMarketEntity bookMarketEntityToAdd) {
        if (bookMarketEntityToAdd.getForRent() == 1 || bookMarketEntityToAdd.getForSell() == 1) {
            bookMarketRepository.save(bookMarketEntityToAdd);
        } else throw new BooksExceptions("Needs to sell, or rent");
    }

    public List<BookMarketEntity> findAllByIsbn(String isbn) {

        return bookMarketRepository.findAllByBookIsbn(isbn);
    }

    public List<BookMarketEntity> findAllByUserId(String userUuid) {

        return bookMarketRepository.findAllByUserUuid(userUuid);
    }

    public BookMarketEntity getBookMarketFromOptional(String bookMarketUuId) throws NoSuchElementException {
        return bookMarketRepository.findByBookMarketUuid(bookMarketUuId).get();
    }
    public String getBookMarketStatus(String bookMarketUuId){
        return getBookMarketFromOptional(bookMarketUuId).getBookStatus();
    }
    public boolean isBookMarketForRent(String bookMarketUuId){
        return getBookMarketFromOptional(bookMarketUuId).getForRent() == 1;
    }
    public boolean isBookMarketForSell(String bookMarketUuId){
        return getBookMarketFromOptional(bookMarketUuId).getForSell() == 1;
    }

    public String getBookIsbn(String bookMarketUuId){
        return getBookMarketFromOptional(bookMarketUuId).getBookIsbn();
    }

    public Double getPriceByMarketBookId(String bookMarketUuid) {
        return bookMarketRepository.getPriceByUuid(bookMarketUuid);
    }
    public Double moneyToPoints(Double bookMarketPrice){
        return (-1)*bookMarketPrice*10;
    }



}




