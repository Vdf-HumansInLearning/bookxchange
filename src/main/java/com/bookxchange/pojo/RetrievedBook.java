package com.bookxchange.pojo;

import com.bookxchange.model.BookEntity;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class RetrievedBook {

    @NotNull
    private boolean retrievedInfo = false;
    @Nullable
    private BookEntity retrievedBook = new BookEntity();
    private String isbn;

    public RetrievedBook(String isbn) {
        retrievedBook.setIsbn(isbn);
    }
}
