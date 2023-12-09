package com.example.rentservice.repository;

import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.building.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    List<BuildingEntity> findAllByAddressLike(AddressEntity address);
    Optional<BuildingEntity> findByAddress(AddressEntity address);
}
