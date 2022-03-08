package com.bookxchange.repositories;

import com.bookxchange.model.BookMarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookMarketRepository extends JpaRepository<BookMarketEntity, String> {
    @Modifying
    @Query(value ="UPDATE book_market SET book_status = ?1 WHERE book_market_id =?2" ,nativeQuery = true)
    void updateBookMarketStatusForId( String status, String bookMarketID);

    @Query(value = "SELECT * FROM book_market WHERE book_id =?1", nativeQuery = true)
    BookMarketEntity getBookMarketEntityByBookId(String bookID);
    }


