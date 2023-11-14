package com.example.rentservice.repository;

import com.example.rentservice.entity.room.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
    Optional<TypeEntity> findById(Long id);

    Optional<TypeEntity> findByText(String text);
}
