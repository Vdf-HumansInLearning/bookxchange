package com.bookxchange.repository;

import com.bookxchange.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
