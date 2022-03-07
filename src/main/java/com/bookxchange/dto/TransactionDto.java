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

    private String memberIdFrom;

    private String memberIdTo;

    private String transactionType;

    private LocalDate transactionDate;

    private LocalDate expectedReturnDate;

}
