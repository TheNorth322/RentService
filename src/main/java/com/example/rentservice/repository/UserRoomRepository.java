package com.example.rentservice.repository;

import com.example.rentservice.entity.room.UserRoomEntity;
import com.example.rentservice.entity.room.UserRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoomEntity, Long> {
    Optional<UserRoomEntity> findById(UserRoomKey id);
}
