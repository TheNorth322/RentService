package com.example.rentservice.repository;

import com.example.rentservice.entity.AddressPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressPartRepository extends JpaRepository<AddressPartEntity, Long> {
    @Query("SELECT ap FROM AddressPartEntity ap WHERE ap.objectGuid = :objectGuid")
    Optional<AddressPartEntity> findByObjectGuid(@Param("objectGuid") String objectGuid);
}
