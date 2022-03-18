package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books", schema = "bookOLX")
public class BooksEntity {
    @Id
    @Column(name = "isbn")
    private String isbn;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "author")
    private String author;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "authors",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Collection<AuthorsEntity> categories;




}
