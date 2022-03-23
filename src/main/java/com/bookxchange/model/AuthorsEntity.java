package com.bookxchange.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors", schema = "bookOLX")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuthorsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "author_id")
    private Integer id;
    @Column(name = "authors_uuid")
    private String authorsUuid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private Set<BooksEntity> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorsEntity authors = (AuthorsEntity) o;
        return id != null && Objects.equals(id, authors.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
