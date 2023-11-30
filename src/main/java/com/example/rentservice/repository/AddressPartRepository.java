package com.example.rentservice.repository;

import com.example.rentservice.entity.AddressPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressPartRepository extends JpaRepository<AddressPartEntity, Long> {
    Optional<AddressPartEntity> findByObjectGuid(String objectGuid);
}
