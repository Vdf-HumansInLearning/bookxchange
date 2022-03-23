package com.bookxchange.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books", schema = "bookOLX")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BooksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "isbn")
    private String isbn;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "authors",
            joinColumns = @JoinColumn(name = "id")
    )
    private Set<AuthorsEntity> authors = new HashSet<>();


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
