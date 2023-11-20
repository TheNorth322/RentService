package com.example.rentservice.entity;

import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
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
    private IndividualUserEntity individualUser;

    @OneToOne(mappedBy = "address")
    private BuildingEntity building;
}
