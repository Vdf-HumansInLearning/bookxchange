package com.bookxchange.service;

import com.bookxchange.model.BooksEntity;
import com.bookxchange.repositories.BooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookService {


    private final BooksRepository bookRepository;

    public BookService(BooksRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    public BooksEntity retrieveBookFromDB(String providedIsbn) {
        return bookRepository.getByIsbn(providedIsbn);
    }

    public void userAddsNewBook(BooksEntity providedBook) {

        providedBook.setQuantity(1);
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
}