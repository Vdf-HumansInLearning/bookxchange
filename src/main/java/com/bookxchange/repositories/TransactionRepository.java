package com.bookxchange.repositories;

import com.bookxchange.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
     TransactionEntity findAllById(Long id);
     List<TransactionEntity> findByTransactionType(String transactionType);
}
