package com.bookxchange.repository;

import com.bookxchange.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends JpaRepository<AuthorEntity, Integer> {

    boolean existsByNameAndSurname(String name, String surname);

    AuthorEntity findAuthorsEntityByNameEqualsAndSurnameEquals(String name, String surname);

    int countAuthorsEntityByNameAndSurname(String name, String surname);
}
