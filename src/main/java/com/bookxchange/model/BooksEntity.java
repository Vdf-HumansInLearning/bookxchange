package com.bookxchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class BooksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_id")
    @JsonIgnore
    private Integer bookId;
    @Column(name = "isbn")
    @NotNull
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN can't be empty, it needs to 10 or 13 digits long (No dashes, or spaces are allowed)")
    private String isbn;
    @Basic
    @Column(name = "title")
    @NotNull
    @Size(min=1, message = "Title must be empty and at least 1 charter long")
    private String title;
    @Basic
    @Column(name = "description")
    @NotNull
    @Size(min=25, message = "Description must not be empty and at least 25 charters long")
    private String description;
    @Basic
    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(
            name = "authors_books_mtm",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "authors_uuid")
    )
    private Set<AuthorsEntity> authors = new HashSet<AuthorsEntity>();

    public BooksEntity() {
    }

    public BooksEntity(String isbn, String title, String description, Integer quantity, Set<AuthorsEntity> authors) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.authors = authors;

//         ITERARE SET
//        DACA EXISTA AUTOR CREAZA M2M SI SCOATE DIN SET
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BooksEntity that = (BooksEntity) o;
        return bookId != null && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
