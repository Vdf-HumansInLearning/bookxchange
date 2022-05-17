package com.bookxchange.service;

import com.bookxchange.model.AuthorEntity;
import com.bookxchange.repository.AuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorsService {

    @Autowired
    private final AuthorsRepository workingAuthorsRepository;

    Logger logger = LoggerFactory.getLogger(AuthorsService.class);

    public AuthorsService(AuthorsRepository workingAuthorsRepository) {
        this.workingAuthorsRepository = workingAuthorsRepository;
    }

    @Transactional
    public void addAuthor(AuthorEntity authorToCheck) {
        logger.debug("Started adding author");
        if (!workingAuthorsRepository.existsByNameAndSurname(authorToCheck.getName(), authorToCheck.getSurname())) {
            workingAuthorsRepository.save(authorToCheck);
        }
    }

    public int getAuthorCountFromDataBaseFullName(String name, String surname) {
        return workingAuthorsRepository.countAuthorsEntityByNameAndSurname(name, surname);
    }
}
