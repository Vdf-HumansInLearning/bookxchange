package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import lombok.*;
import org.springframework.transaction.TransactionStatus;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionDto {

    private String marketBookIdSupplier;
    private String marketBookIdClient;
    private String supplier;
    private String client;
    private TransactionType transactionType;


}
