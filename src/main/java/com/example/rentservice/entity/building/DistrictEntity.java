package com.example.rentservice.entity.building;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "districts")
public class DistrictEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "district")
    private Set<BuildingEntity> buildings;

    private String name;
}
