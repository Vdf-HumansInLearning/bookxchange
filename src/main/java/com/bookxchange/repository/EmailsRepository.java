package com.bookxchange.repository;

import com.bookxchange.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<EmailEntity, Integer> {
}
