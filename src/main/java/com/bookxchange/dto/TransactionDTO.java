package com.bookxchange.dto;

import com.bookxchange.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @NotBlank(message = "marketBookIdSupplier is mandatory")
    private String marketBookUuidSupplier;
    private String marketBookUuidClient;
    @NotBlank(message = "supplierId is mandatory")
    private String supplierId;
    @JsonIgnore
    private String clientId;
    private TransactionType transactionType;


}
