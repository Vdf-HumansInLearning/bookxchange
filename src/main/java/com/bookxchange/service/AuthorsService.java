package com.bookxchange.service;

import com.bookxchange.model.AuthorsEntity;
import com.bookxchange.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorsService {

    @Autowired
    private final AuthorsRepository workingAuthorsRepository;


    public AuthorsService(AuthorsRepository workingAuthorsRepository) {
        this.workingAuthorsRepository = workingAuthorsRepository;
    }

    @Transactional
    public void addAuthor(AuthorsEntity authorToCheck){
        System.out.println("Started adding author");
         if(!workingAuthorsRepository.existsByNameAndSurname(authorToCheck.getName(), authorToCheck.getSurname())){
             System.out.println("Entered If");
             workingAuthorsRepository.save(authorToCheck);
         }
    }
}
