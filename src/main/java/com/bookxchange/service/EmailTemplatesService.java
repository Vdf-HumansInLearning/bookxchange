package com.bookxchange.service;

import com.bookxchange.model.EmailTemplatesEntity;
import com.bookxchange.repository.EmailTemplatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmailTemplatesService {

    @Autowired
    EmailTemplatesRepository emailTemplatesRepository;

    public EmailTemplatesEntity getById(Integer id) {
       Optional<EmailTemplatesEntity> emailTemplatesEntity=emailTemplatesRepository.findEmailTemplatesEntityById(id);
         if(emailTemplatesEntity.isPresent()){
          return emailTemplatesEntity.get();
         }
        else {
            throw new NoSuchElementException("No template found by this id");
         }
    }
}
