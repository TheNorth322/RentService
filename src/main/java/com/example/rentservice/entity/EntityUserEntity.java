package com.example.rentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "entity_user")
public class EntityUserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "supervisor_full_name")
    private String supervisorFullName;

    private String address;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "checking_account")
    private String checkingAccount;

    @Column(name = "itn_number")
    private String itnNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
