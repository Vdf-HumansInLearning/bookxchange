package com.bookxchange.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors", schema = "bookOLX")
@Data
public class AuthorsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private Set<BooksEntity> books;
}
