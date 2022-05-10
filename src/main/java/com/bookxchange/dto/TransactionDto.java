package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @NotBlank(message = "marketBookIdSupplier is mandatory")
    private String marketBookIdSupplier;
    private String marketBookIdClient;
    @NotBlank(message = "supplierId is mandatory")
    private String supplierId;
    @NotBlank(message = "clientId is mandatory")
    private String clientId;

    private TransactionType transactionType;


}
