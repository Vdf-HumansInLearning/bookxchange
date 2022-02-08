package com.bookxchange.model;

import com.bookxchange.enums.TransactionType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private long marketBookId;
    private long memberIdFrom;
    private long memberIdTo;
    private TransactionType transactionType;
    private Date rentDate;
    private Date expectedReturn;


    public Transaction(UUID id, long marketBookId, long memberIdFrom, long memberIdTo, Date rentDate) {
        this.id = id;
        this.marketBookId = marketBookId;
        this.memberIdFrom = memberIdFrom;
        this.memberIdTo = memberIdTo;
        this.rentDate = rentDate;
    }
}
