package com.bookxchange.controller;

import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final Mapper mapper = new Mapper();
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<TransactionEntity> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionEntity transactionEntity = mapper.toTransaction(transactionDto);
        transactionService.createTransaction(transactionEntity);
        return new ResponseEntity<>(transactionEntity, HttpStatus.CREATED);
    }

    @GetMapping("/transactions/id")
    public ResponseEntity<TransactionEntity> getTransactionById(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }
    @GetMapping("/transactions/type")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByType(@RequestParam("type") String type) {
        return new ResponseEntity<>(transactionService.getTransactionByType(type), HttpStatus.OK);
    }

}
