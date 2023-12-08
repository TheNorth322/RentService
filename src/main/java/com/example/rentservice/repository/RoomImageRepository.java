package com.example.rentservice.repository;

import com.example.rentservice.entity.room.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Long> {
}
