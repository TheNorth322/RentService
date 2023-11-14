package com.example.rentservice.repository;

import com.example.rentservice.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, Long> {

}
