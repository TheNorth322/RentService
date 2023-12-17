package com.example.rentservice.repository;

import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRoomRepository extends JpaRepository<AgreementRoomEntity, Long> {

}
