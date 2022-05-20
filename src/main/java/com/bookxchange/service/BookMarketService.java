package com.bookxchange.service;

import com.bookxchange.exception.BookExceptions;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.repository.BookMarketRepository;
import com.bookxchange.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class BookMarketService {
    private final BookMarketRepository bookMarketRepository;
    private final BooksRepository workingBookRepository;

    @Autowired
    public BookMarketService(BookMarketRepository bookMarketRepository, BooksRepository workingBookRepository) {
        this.bookMarketRepository = bookMarketRepository;
        this.workingBookRepository = workingBookRepository;
    }

    public void updateBookMarketStatus(String status, String bookMarketID) {
        bookMarketRepository.updateBookMarketStatusForId(status, bookMarketID);
    }


    public String addBookMarketEntityAndBookIfCustom(BookListing retrievedBookListing) {

        if (retrievedBookListing.getReceivedBookMarket().getForRent() == 1 ||
                retrievedBookListing.getReceivedBookMarket().getForSell() == 1) {
                if(!retrievedBookListing.isDataIsRetrievedDb()) {
                    workingBookRepository.save(retrievedBookListing.getReceivedBook());
                }
                bookMarketRepository.save(retrievedBookListing.getReceivedBookMarket());
        } else throw new BookExceptions("Needs to sell, or rent");

        return String.format("Your market entry for %s has been added successfully", retrievedBookListing.getReceivedBook().getTitle());
        }

    public List<BookMarketEntity> findAllByIsbn(String isbn) {

        return bookMarketRepository.findAllByBookIsbn(isbn);
    }

    public List<BookMarketEntity> findAllByUserId(String userUuid) {

        return bookMarketRepository.findAllByUserUuid(userUuid);
    }

    public BookMarketEntity getBookMarketFromOptional(String bookMarketUuId) throws NoSuchElementException {
        Optional<BookMarketEntity> bookMarket = bookMarketRepository.findByBookMarketUuid(bookMarketUuId);
        if (bookMarket.isPresent())
            return bookMarket.get();
        else throw new NoSuchElementException("Can't find the book by this id");

    }

    public String getBookIsbn(String bookMarketUuId) {

        return getBookMarketFromOptional(bookMarketUuId).getBookIsbn();
    }

    public Double getPriceByMarketBookId(String bookMarketUuid) {
        return bookMarketRepository.getPriceByUuid(bookMarketUuid);
    }

    public Double moneyToPoints(Double bookMarketPrice) {
        return (-1) * bookMarketPrice * 10;
    }

    public void deleteBookMarketEntry(String uuidToDelete) {
        bookMarketRepository.deleteByBookMarketUuid(uuidToDelete);
    }

}




