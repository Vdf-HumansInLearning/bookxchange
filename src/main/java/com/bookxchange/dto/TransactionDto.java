package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionDto {

    private String marketBookId;
    private String supplier;
    private String client;
    private TransactionType transactionType;

}
