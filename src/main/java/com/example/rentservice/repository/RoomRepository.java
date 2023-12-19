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
    @Query("SELECT r FROM RoomEntity r " +
            "LEFT JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "WHERE ar.room.id IS NULL OR ar.endOfRent < CURRENT_TIMESTAMP")
    List<RoomEntity> findAvailableRooms();
    List<RoomEntity> findAllByTypesContains(TypeEntity type);
    @Query("SELECT r FROM RoomEntity r " +
            "LEFT JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "WHERE (ar.room.id IS NULL OR ar.endOfRent < CURRENT_TIMESTAMP) " +
            "AND r.building = :building")
    List<RoomEntity> findAvailableRoomsInBuilding(@Param("building") BuildingEntity building);

    @Query("SELECT b.name AS building_name, r.number AS room_number, COUNT(ar.room.id) AS rental_count " +
            "FROM BuildingEntity b " +
            "JOIN RoomEntity r ON b.id = r.building.id " +
            "LEFT JOIN AgreementRoomEntity ar ON r.id = ar.room.id " +
            "GROUP BY b.name, r.number " +
            "HAVING COUNT(ar.room.id) > 0")
    List<RoomEntity> findRoomsInBuildingsWithRentals();
}
