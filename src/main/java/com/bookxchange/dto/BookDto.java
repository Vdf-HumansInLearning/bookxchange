package com.bookxchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    String bookTitle;
    String authorsName;
    String authorSurname;
    int bookQuantity;
}
