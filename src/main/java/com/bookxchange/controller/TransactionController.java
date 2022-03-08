package com.bookxchange.controller;

import com.bookxchange.dto.Mapper;
import com.bookxchange.dto.TransactionDto;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.service.BookMarketService;
import com.bookxchange.service.MemberService;
import com.bookxchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final Mapper mapper = new Mapper();
    private final MemberService memberService;
    private final BookMarketService bookMarketService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(MemberService memberService, BookMarketService bookMarketService, TransactionService transactionService) {
        this.memberService = memberService;
        this.bookMarketService = bookMarketService;
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionEntity> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionEntity transactionEntity = mapper.toTransaction(transactionDto);
        transactionService.createTransaction(transactionEntity);
        if(transactionDto.getTransactionType().equalsIgnoreCase("RENT")){
           memberService.updatePointsToMemberByID(transactionDto.getMemberIdFrom());
           bookMarketService.updateBookMarketStatus("RENTED", transactionDto.getMarketBookId());
        }else {
            bookMarketService.updateBookMarketStatus("SOLD", transactionDto.getMarketBookId());
        }

        return new ResponseEntity<>(transactionEntity, HttpStatus.CREATED);
    }

    @GetMapping("/transactions/")
    public ResponseEntity<List<TransactionEntity>> getTransactionById(@RequestParam String userID) {
        return new ResponseEntity<>(transactionService.getTransactionByUserID(userID), HttpStatus.OK);
    }


    @GetMapping("/transactions/a")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByType(@RequestParam String type) {
        return new ResponseEntity<>(transactionService.getTransactionByType(type), HttpStatus.OK);
    }

}
