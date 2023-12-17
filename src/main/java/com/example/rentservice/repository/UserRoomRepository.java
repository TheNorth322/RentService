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
    @Query("SELECT u FROM UserRoomEntity u WHERE u.id.userId = :userId ORDER BY u.startOfRent ASC")
    Optional<UserRoomEntity> findFirstByUserIdOrderByStartOfRentAsc(@Param("userId") Long userId);

    @Query("SELECT u FROM UserRoomEntity u WHERE u.id.userId = :userId ORDER BY u.endOfRent DESC")
    Optional<UserRoomEntity> findFirstByUserIdOrderByEndOfRentDesc(@Param("userId") Long userId);
}
