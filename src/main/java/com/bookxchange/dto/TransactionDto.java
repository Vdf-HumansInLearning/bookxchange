package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TransactionDto {

    @NotBlank(message = "marketBookId is mandatory")
    private final String marketBookId;
    @NotBlank(message = "supplier is mandatory")
    private final String supplier;
    @NotBlank(message = "client is mandatory")
    private final String client;
    @NotBlank(message = "transactionType is mandatory")
    private final TransactionType transactionType;

}
