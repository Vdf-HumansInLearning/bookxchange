package com.bookxchange.service;

import com.bookxchange.model.AuthorEntity;
import com.bookxchange.repository.AuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorsService {

    private final AuthorsRepository workingAuthorsRepository;

    Logger logger = LoggerFactory.getLogger(AuthorsService.class);

    @Autowired
    public AuthorsService(AuthorsRepository workingAuthorsRepository) {
        this.workingAuthorsRepository = workingAuthorsRepository;
    }

    public AuthorEntity attemptAuthorDatabaseRetrival(AuthorEntity authorToCheck) {
        AuthorEntity tempAuthor = workingAuthorsRepository.findAuthorsEntityByNameEqualsAndSurnameEquals(authorToCheck.getName(), authorToCheck.getSurname());
        logger.debug("Started adding author");
        if (tempAuthor == null) {
            tempAuthor = new AuthorEntity();
//           tempAuthor.setAuthorsUuid(UUID.randomUUID().toString());
            System.out.println("A Trecut de RAndom UUID AUTOR");
        }
        return tempAuthor;
    }

    public int getAuthorCountFromDataBaseFullName(String name, String surname) {
        return workingAuthorsRepository.countAuthorsEntityByNameAndSurname(name, surname);
    }
}
