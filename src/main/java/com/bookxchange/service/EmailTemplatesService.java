package com.bookxchange.service;

import com.bookxchange.model.EmailTemplatesEntity;
import com.bookxchange.repositories.EmailTemplatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplatesService {

    @Autowired
    EmailTemplatesRepository emailTemplatesRepository;

    public EmailTemplatesEntity getById( Integer id){

        return  emailTemplatesRepository.findById(id).get();
    }
}
