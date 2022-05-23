package com.bookxchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuthorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "author_id")
    private Integer id;
    @Column(name = "authors_uuid")
    @NotNull
    private String authorsUuid;
    @Basic
    @Column(name = "name")
    @NotNull
    @Pattern(regexp = "^.{1,}$", message = "Author name needs to be formed only out of letters, a minimum of 3 charters are required")
    private String name;
    @Basic
    @Column(name = "surname")
    @NotNull
    @Pattern(regexp = "^.{1,}$", message = "Author surname needs to be formed only out of letters, a minimum of 3 charters are required")
    private String surname;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Transient
    @JsonIgnore
    private List<BookEntity> books;

    public AuthorEntity(String name, String surname) {
        this.authorsUuid = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorEntity authors = (AuthorEntity) o;
        return id != null && Objects.equals(id, authors.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
