package com.example.rentservice.repository;

import com.example.rentservice.entity.building.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    List<BuildingEntity> findAllByAddressLike(String address);
}
