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

    public BooksEntity retriveBookFromDB(String providedIsbn){
        BooksEntity bookToReturn = bookRepository.getByIsbn(providedIsbn);
        return bookToReturn;
    }

    public void userAddsNewBook(BooksEntity providedBook) {

            providedBook.setQuantity(1);
            bookRepository.save(providedBook);
//
//        bookRepository.updateQuantityAdd(providedBook.getIsbn());
    }

    @Transactional
    public void updateQuantityAtAdding(String providedIsbn) {
        bookRepository.updateQuantityAdd(providedIsbn);
    }

    @Transactional
    public void downgradeQuantityForTransaction(String providedIsbn) {
        bookRepository.downgradeQuantityForTransaction(providedIsbn);
    }

}