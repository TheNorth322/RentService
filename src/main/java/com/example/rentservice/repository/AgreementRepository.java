package com.example.rentservice.repository;

import com.example.rentservice.entity.agreement.AgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<AgreementEntity, Long> {
    Optional<AgreementEntity> findTopByOrderByRegistrationNumberDesc();
}
