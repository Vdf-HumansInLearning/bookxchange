package com.bookxchange.controller;

import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.BookService;
import com.bookxchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {


    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService, BookService bookService, BookMarketService bookMarketService) {
        this.transactionService = transactionService;
    }

    @PostMapping("")
    public ResponseEntity<TransactionEntity> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        TransactionEntity transactionEntity = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(transactionEntity, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByMemberUuID(@RequestParam String userID) {
        return new ResponseEntity<>(transactionService.getTransactionByMemberUuid(userID), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByMemberUuIDAndType(@RequestParam String userID,
                                                                                      @RequestParam TransactionType type) {
        return new ResponseEntity<>(transactionService.getTransactionsByMemberUuIDAndType(userID, type), HttpStatus.OK);
    }


}
