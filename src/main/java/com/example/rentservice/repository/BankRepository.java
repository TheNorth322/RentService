package com.example.rentservice.repository;

import com.example.rentservice.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
    List<BankEntity> findAllByNameLike(String name);
    Optional<BankEntity> findByName(String name);
}
