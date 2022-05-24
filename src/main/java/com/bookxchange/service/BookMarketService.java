package com.bookxchange.service;

import com.bookxchange.exception.BookExceptions;
import com.bookxchange.model.BookMarketEntity;
import com.bookxchange.pojo.BookListing;
import com.bookxchange.repository.BookMarketRepository;
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
    private final BookService workingBookService;

    @Autowired
    public BookMarketService(BookMarketRepository bookMarketRepository, BookService workingBookService) {
        this.bookMarketRepository = bookMarketRepository;
        this.workingBookService = workingBookService;
    }

    public void updateBookMarketStatus(String status, String bookMarketID) {
        bookMarketRepository.updateBookMarketStatusForId(status, bookMarketID);
    }


    public String addBookMarketEntityAndBookIfCustom(BookListing retrievedBookListing) {

        if (retrievedBookListing.getReceivedBookMarket().getForRent() == 1 ||
                retrievedBookListing.getReceivedBookMarket().getForSell() == 1) {
                if(!retrievedBookListing.isDataIsRetrievedDb()) {
                    workingBookService.addNewBookToDB(retrievedBookListing.getReceivedBook());

                }
                bookMarketRepository.save(retrievedBookListing.getReceivedBookMarket());
        } else throw new BookExceptions("Needs to sell, or rent");
        workingBookService.updateQuantityAtAdding(retrievedBookListing.getReceivedBookMarket().getBookIsbn());

        return String.format("Your market entry for %s has been added successfully", retrievedBookListing.getReceivedBook().getTitle());
        }

    public List<BookMarketEntity> findAllByIsbn(String isbn) {
        List<BookMarketEntity> bookMarketList = bookMarketRepository.findBookMarketEntityByBookIsbn(isbn);
        if(!bookMarketList.isEmpty()){
            return bookMarketList;

        }else {
            throw new BookExceptions("No entry for this isbn");
        }
    }

    public List<BookMarketEntity> findAllByUserId(String userUuid) {
        List<BookMarketEntity> bookMarketList = bookMarketRepository.findAllByUserUuid(userUuid);
        if(!bookMarketList.isEmpty()){
            return bookMarketList;
        }else {
            throw new BookExceptions("No entry for this userUuid");
        }

    }

    public BookMarketEntity getBookMarketFromOptional(String bookMarketUuId) throws NoSuchElementException {
        Optional<BookMarketEntity> bookMarket = bookMarketRepository.findByBookMarketUuid(bookMarketUuId);
        if (bookMarket.isPresent())
            return bookMarket.get();

        else throw new BookExceptions("Can't find the book by this uuId");


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

        Optional<BookMarketEntity> bookMarketCheck = bookMarketRepository.getBookMarketEntityByBookMarketUuid(uuidToDelete);

        if (bookMarketCheck.isPresent()) {
            workingBookService.downgradeQuantityForTransaction(bookMarketCheck.get().getBookIsbn());
            bookMarketRepository.deleteByBookMarketUuid(uuidToDelete);
        } else {
            throw new BookExceptions(String.format("The book market entry with this UUID %s is not present", uuidToDelete));
        }
    }

}




