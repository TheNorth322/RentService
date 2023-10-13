package com.example.rentservice.repository;

import com.example.rentservice.entity.user.MigrationServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MigrationServiceRepository extends JpaRepository<MigrationServiceEntity, Long> {
    Optional<MigrationServiceEntity> findByName(String name);

    List<MigrationServiceEntity> findAllByNameLike(String name);
}
