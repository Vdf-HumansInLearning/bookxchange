package com.bookxchange.repository;

import com.bookxchange.model.EmailTemplatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplatesEntity, Integer> {

    Optional<EmailTemplatesEntity> findEmailTemplatesEntityById(Integer id);

}
