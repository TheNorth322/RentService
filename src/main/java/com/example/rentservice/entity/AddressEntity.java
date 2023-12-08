package com.example.rentservice.entity;

import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.user.EntityUserEntity;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.entity.user.PassportEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "addresses")
@EqualsAndHashCode(exclude = {"migrationService", "entityUser", "passports", "building", "addressParts"})
@ToString(exclude = {"migrationService", "entityUser", "passports", "building", "addressParts"})
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "address")
    private MigrationServiceEntity migrationService;

    @OneToOne(mappedBy = "address")
    private EntityUserEntity entityUser;

    @OneToMany(mappedBy = "placeOfBirth")
    private Set<PassportEntity> passports;

    @OneToOne(mappedBy = "address")
    private BuildingEntity building;

    @ManyToMany
    @JoinTable(
            name = "address_address_parts",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "address_part_id")
    )
    private Set<AddressPartEntity> addressParts;
}
