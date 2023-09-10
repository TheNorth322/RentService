package com.example.rentservice.entity;

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
@Table(name = "buildings")
public class BuildingEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String district;

    private String address;

    @Column(name = "floor_count")
    private Integer floorCount;

    private String telephone;

    @OneToMany(mappedBy = "building")
    private Set<RoomEntity> rooms;
}
