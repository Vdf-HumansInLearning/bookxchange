package com.bookxchange.model;

import com.bookxchange.enums.TransactionType;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Transaction {

    private UUID id;
    private long marketBookId;
    private long memberIdFrom;
    private long memberIdTo;
    private TransactionType transactionType;
    private Date rentDate;
    private Date expectedReturn;



}
