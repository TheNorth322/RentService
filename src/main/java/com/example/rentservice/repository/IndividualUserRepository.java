package com.example.rentservice.repository;

import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndividualUserRepository extends JpaRepository<IndividualUserEntity, Long> {
    Optional<IndividualUserEntity> findByUser_Username(String username);
    @Query("SELECT iu FROM IndividualUserEntity iu " +
            "JOIN PassportEntity p ON iu.activePassport.id = p.id " +
            "WHERE p.migrationService = :migrationService")
    List<IndividualUserEntity> findAllByPassportMigrationService(@Param("migrationService") MigrationServiceEntity migrationService);

}
