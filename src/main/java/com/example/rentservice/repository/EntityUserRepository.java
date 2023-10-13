package com.example.rentservice.repository;

import com.example.rentservice.entity.user.EntityUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityUserRepository extends JpaRepository<EntityUserEntity, Long> {
}
