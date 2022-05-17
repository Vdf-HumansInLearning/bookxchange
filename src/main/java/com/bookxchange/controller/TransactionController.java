package com.bookxchange.controller;

import com.bookxchange.dto.TransactionDTO;
import com.bookxchange.enums.TransactionType;
import com.bookxchange.model.TransactionEntity;
import com.bookxchange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("")
    public ResponseEntity<TransactionEntity> createTransaction(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody TransactionDTO transactionDto) {

        TransactionEntity transactionEntity = transactionService.createTransaction(transactionDto, token);
        return new ResponseEntity<>(transactionEntity, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionEntity>> getTransactionsByMemberUuIDAndType(@RequestParam String userID,
                                                                                      @RequestParam(required = false) TransactionType type) {
        if (type == null) {
            return new ResponseEntity<>(transactionService.getTransactionByMemberUuid(userID), HttpStatus.OK);
        }
        return new ResponseEntity<>(transactionService.getTransactionsByMemberUuIDAndType(userID, type), HttpStatus.OK);
    }

    @GetMapping("/trade/decision")
//    TODO: sa cosmetizam url astfel incat userul sa nu vada id-urile in link-ul de email. Am gasit o metoda dar nu am avut timp de implementare
    public void approveTradingTransaction(@RequestParam String answerToTrade, @RequestParam String transactionId) {
        transactionService.updateTransactionByUserTradeDecision(answerToTrade, transactionId);
    }
}
