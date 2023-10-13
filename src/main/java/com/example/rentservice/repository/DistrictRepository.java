package com.example.rentservice.repository;

import com.example.rentservice.entity.building.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
    Optional<DistrictEntity> findByName(String name);
    List<DistrictEntity> findAllByNameLike(String name);
}
