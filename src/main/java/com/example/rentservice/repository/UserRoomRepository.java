package com.example.rentservice.repository;

import com.example.rentservice.entity.room.UserRoomEntity;
import com.example.rentservice.entity.room.UserRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoomEntity, Long> {
    Optional<UserRoomEntity> findById(UserRoomKey id);

    List<UserRoomEntity> findById_UserId(Long id);

    Optional<UserRoomEntity> findById_UserIdAndId_RoomId(Long userId, Long roomId);
    Optional<UserRoomEntity> findFirstById_UserIdOrderByStartOfRentAsc(Long id);
    Optional<UserRoomEntity> findFirstById_UserIdOrderByEndOfRentDesc(Long id);
}
