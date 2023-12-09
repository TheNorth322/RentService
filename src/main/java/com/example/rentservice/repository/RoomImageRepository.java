package com.example.rentservice.repository;

import com.example.rentservice.entity.room.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Long> {
    Optional<RoomImageEntity> findByUrl(String url);
}
