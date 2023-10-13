package com.example.rentservice.repository;

import com.example.rentservice.entity.user.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
}
