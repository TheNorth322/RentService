package com.example.rentservice.repository;

import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementRoomRepository extends JpaRepository<AgreementRoomEntity, Long> {
    List<AgreementRoomEntity> findAllByAgreement_Id(Long id);
}
