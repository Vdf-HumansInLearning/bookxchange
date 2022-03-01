package com.bookxchange.repositories;

import com.bookxchange.model.EmailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<EmailsEntity, Integer> {
}
