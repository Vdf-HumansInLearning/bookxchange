package com.bookxchange.Model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Book {

    @NotNull(message = "ISBN cannot be null")
    @Size(min=13,max=13, message
        = "must be 13 chars")
    private long isbn;
    private String title;
    private String description;
    private Boolean sellAvailability;
    private Boolean rentAvailability;
    private Integer quantity;
    private List<Author> author;

}
