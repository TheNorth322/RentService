package com.example.rentservice.repository;

import com.example.rentservice.entity.user.IndividualUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualUserRepository extends JpaRepository<IndividualUserEntity, Long> {
    Optional<IndividualUserEntity> findByUser_Username(String username);
}
