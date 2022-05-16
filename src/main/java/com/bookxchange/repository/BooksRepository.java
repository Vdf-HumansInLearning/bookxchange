package com.bookxchange.repository;

import com.bookxchange.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface BooksRepository extends JpaRepository<BookEntity, Integer> {

    BookEntity getByIsbn(String providedIsbn);

    @Modifying
    @Query(value = "UPDATE books SET books.quantity =books.quantity+1 WHERE books.isbn = ?1", nativeQuery = true)
    void updateQuantityAdd(String isbn);

    @Modifying
    @Query(value = "UPDATE books SET quantity = quantity-1 WHERE isbn = ?1", nativeQuery = true)
    void downgradeQuantityForTransaction(String isbn);

    @Query(value = "SELECT quantity FROM books WHERE isbn = ?1", nativeQuery = true)
    Integer getQuantityByIsbn(String isbn);


    ArrayList<BookEntity> findAll();

}