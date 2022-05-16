package com.bookxchange.repository;

import com.bookxchange.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByMemberuuIdFrom(String memberIdFrom);

    @Query(value = "SELECT * FROM transaction WHERE transaction.member_uuid_from =?1  AND transaction.transaction_type =?2", nativeQuery = true)
    List<TransactionEntity> findAllByMemberUuIDAndTransactionType(String memberUuid, String type);


    @Query(value = "SELECT * FROM transaction WHERE transaction.market_book_uuid =?1  AND transaction.member_uuid_to =?2", nativeQuery = true)
    List<TransactionEntity> getTransactionsByBookIdAndLeftBy(String marketBookId, String memberIdTo);

    @Query(value = "SELECT * FROM transaction WHERE transaction.member_uuid_from =?1 AND transaction.member_uuid_to =?2", nativeQuery = true)
    List<TransactionEntity> getTransactionByWhoSelleddAndWhoBuys(String memberIdFrom, String memberIdTo);

    List<TransactionEntity> findAllByTransactionDate(LocalDate transactionDate);

    List<TransactionEntity> findTransactionEntityByMarketBookIdClientAndMarketBookIdSupplierAndTransactionTypeAndTransactionStatus(String MarketBookUuidClient, String MarketBookUuidSupplier, String transactionType, String transactionStatus);

}
