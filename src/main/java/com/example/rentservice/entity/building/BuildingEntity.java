package com.example.rentservice.entity.building;

import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.room.RoomEntity;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "floor_count")
    private Integer floorCount;

    private String telephone;

    @OneToMany(mappedBy = "building")
    private Set<RoomEntity> rooms;
}
