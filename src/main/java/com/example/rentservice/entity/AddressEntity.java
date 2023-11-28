package com.example.rentservice.entity;

import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.user.EntityUserEntity;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.entity.user.PassportEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "fias_id")
    private String fiasId;

    private String name;

    @OneToOne(mappedBy = "address")
    private MigrationServiceEntity migrationService;

    @OneToOne(mappedBy = "address")
    private EntityUserEntity entityUser;

    @OneToMany(mappedBy = "placeOfBirth")
    private Set<PassportEntity> passports;

    @OneToOne(mappedBy = "address")
    private BuildingEntity building;
}
