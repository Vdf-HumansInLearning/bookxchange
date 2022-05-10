package com.bookxchange.service;

import com.bookxchange.model.BooksEntity;
import com.bookxchange.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class BookService {

    @Autowired
    private final BooksRepository bookRepository;
    @Autowired
    private final AuthorsService workingAuthorsService;

    public BookService(BooksRepository bookRepository, AuthorsService workingAuthorsService) {
        this.bookRepository = bookRepository;

        this.workingAuthorsService = workingAuthorsService;
    }

    public BooksEntity retrieveBookFromDB(String providedIsbn) {
        return bookRepository.getByIsbn(providedIsbn);
    }

    public ArrayList<BooksEntity> userRetrievesBookList(){
        return bookRepository.findAll();
    }

    @Transactional
    public void addNewBookToDB(BooksEntity providedBook) {
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

    public int getQuantityByIsbn(String isbn){
       return bookRepository.getQuantityByIsbn(isbn);
    }

    public BooksEntity getBookByIsbn(String isbn){

        return bookRepository.getByIsbn(isbn);
    }

}