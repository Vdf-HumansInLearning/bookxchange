package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionDto {

    @NotBlank(message = "marketBookId is mandatory")
    private String marketBookId;
    @NotBlank(message = "supplier is mandatory")
    private String supplier;
    @NotBlank(message = "client is mandatory")
    private String client;
    @NotBlank(message = "transactionType is mandatory")
    private TransactionType transactionType;

}
