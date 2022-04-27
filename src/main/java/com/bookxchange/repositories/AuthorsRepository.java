package com.bookxchange.repositories;

import com.bookxchange.model.AuthorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorsRepository extends JpaRepository<AuthorsEntity, Integer> {

    boolean existsByNameAndSurname(String name, String surname);

    AuthorsEntity findAuthorsEntityByNameEqualsAndSurnameEquals(String name, String surname);

    int countAuthorsEntityByNameAndSurname(String name, String surname);
}
