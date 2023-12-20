package com.example.rentservice.repository;

import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query("SELECT DISTINCT r FROM RoomEntity r " +
            "LEFT JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "WHERE ar.id IS NULL OR ar.endOfRent < CURRENT_TIMESTAMP")
    List<RoomEntity> findAvailableRooms();

    List<RoomEntity> findAllByTypesContains(TypeEntity type);
    @Query("SELECT DISTINCT r FROM RoomEntity r " +
            "LEFT JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "WHERE (ar.id IS NULL OR ar.endOfRent < CURRENT_TIMESTAMP) " +
            "AND r.building = :building")
    List<RoomEntity> findAvailableRoomsInBuilding(@Param("building") BuildingEntity building);


    @Query("SELECT r FROM RoomEntity r " +
            "JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "WHERE ar.room.id IS NOT NULL " +
            "GROUP BY r.building, r.number, r.id " +
            "HAVING COUNT(ar.room.id) > 0")
    List<RoomEntity> findRoomsInBuildingsWithRentals();

}
