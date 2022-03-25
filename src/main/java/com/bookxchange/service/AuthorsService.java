package com.bookxchange.service;

import com.bookxchange.controller.BooksController;
import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.repositories.AuthorsRepository;
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
    public void addAuthor(AuthorsEntity authorToCheck){
        logger.debug("Started adding author");
         if(!workingAuthorsRepository.existsByNameAndSurname(authorToCheck.getName(), authorToCheck.getSurname())){
             workingAuthorsRepository.save(authorToCheck);
         }
    }
}
