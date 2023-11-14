package com.example.rentservice.repository;

import com.example.rentservice.entity.user.PassportEntity;
import com.example.rentservice.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
    List<PassportEntity> findAllByUser(UserEntity user);
}
