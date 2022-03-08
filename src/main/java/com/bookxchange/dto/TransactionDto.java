package com.bookxchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionDto {

    private String id;

    private String marketBookId;
//buyer seller
    private String memberIdFrom;

    private String memberIdTo;

//transactiontype enum in loc de string
    private String transactionType;

    private LocalDate transactionDate;

    private LocalDate expectedReturnDate;

}
