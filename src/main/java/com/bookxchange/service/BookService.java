package com.bookxchange.service;

import com.bookxchange.model.BookEntity;
import com.bookxchange.pojo.RetrievedBook;
import com.bookxchange.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class BookService {

    @Autowired
    private final BooksRepository bookRepository;
    @Autowired
    private final IsbnService workingIsbnService;

    public BookService(BooksRepository bookRepository, IsbnService workingIsbnService) {
        this.bookRepository = bookRepository;
        this.workingIsbnService = workingIsbnService;
    }

    public BookEntity retrieveBookFromDB(String providedIsbn) {
        return bookRepository.getByIsbn(providedIsbn);
    }

    public ArrayList<BookEntity> userRetrievesBookList(){
        return bookRepository.findAll();
    }

    @Transactional
    public void addNewBookToDB(BookEntity providedBook) {
        bookRepository.save(providedBook);

    }

    @Transactional
    public void updateQuantityAtAdding(String providedIsbn) {
        bookRepository.updateQuantityAdd(providedIsbn);
    }

    @Transactional
    public void downgradeQuantityForTransaction(String providedIsbn) {
        bookRepository.downgradeQuantityForTransaction(providedIsbn);
    }

    public int getQuantityByIsbn(String isbn){
       return bookRepository.getQuantityByIsbn(isbn);
    }

    public BookEntity getBookByIsbn(String isbn){
        return bookRepository.getByIsbn(isbn);
    }

    @Transactional
    public RetrievedBook checkDbOrAttemptToPopulateFromIsbn(String providedIsbn) {

        RetrievedBook retrievedBookToReturn = new RetrievedBook(providedIsbn);
        retrievedBookToReturn.setRetrievedInfo(false);
        retrievedBookToReturn.setIsbn(providedIsbn);

        BookEntity bookToReturn = getBookByIsbn(providedIsbn);

        if (bookToReturn == null) {
            bookToReturn=workingIsbnService.hitIsbnBookRequest(providedIsbn);
            if (bookToReturn != null) {
                retrievedBookToReturn.setRetrievedInfo(true);
                retrievedBookToReturn.setRetrievedBook(bookToReturn);
                bookRepository.save(bookToReturn);
            }
        } else {
            retrievedBookToReturn.setRetrievedBook(bookToReturn);
            retrievedBookToReturn.setRetrievedInfo(true);
        }

        return retrievedBookToReturn;
    }

}