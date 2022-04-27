package com.bookxchange.repositories;

import com.bookxchange.customExceptions.BooksExceptions;
import com.bookxchange.model.BookMarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//    @Query
//    default <S extends BookMarketEntity> S save(S entity) {
////        if(!entity.isForSell() && !entity.isForRent()){
////            throw new BooksExceptions("Something something");
////
////        }
//        return null;
//    }




@Repository
public interface BookMarketRepository extends PagingAndSortingRepository<BookMarketEntity, String> {
    @Modifying
    @Query(value = "UPDATE book_market SET book_status = ?1 WHERE book_market_uuid =?2", nativeQuery = true)
    void updateBookMarketStatusForId(String status, String bookMarketID);

    @Query(value = "SELECT * FROM book_market WHERE book_isbn =?1", nativeQuery = true)
    BookMarketEntity getBookMarketEntityByBookId(String bookID);

    BookMarketEntity getBookMarketEntityByBookMarketUuid(String bookMarketID);

    Optional<BookMarketEntity> findByBookMarketUuid(String uuid);

    List<BookMarketEntity> findAllByBookIsbn(String isbn);

    List<BookMarketEntity> findAllByUserUuid(String userUuid);

    @Query(value = "SELECT sell_price from book_market where book_market_uuid = ?1", nativeQuery = true)
    Double getPriceByUuid(String uuid);

}