package com.example.rentservice.repository;

import com.example.rentservice.entity.user.EntityUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityUserRepository extends JpaRepository<EntityUserEntity, Long> {
    List<EntityUserEntity> findAllByBank_Id(long id);
}
