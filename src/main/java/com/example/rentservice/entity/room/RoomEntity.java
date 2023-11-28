package com.example.rentservice.entity.room;

import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue
    private Long id;

    private boolean telephone;

    private String description;

    private double area;

    private Integer number;

    private Integer floor;
    private Integer price;

    @ManyToMany
    @JoinTable(
            name = "room_types",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<TypeEntity> types;

    @OneToMany(mappedBy = "room")
    private Set<RoomImageEntity> roomImages;

    @OneToMany(mappedBy = "room")
    private Set<AgreementRoomEntity> rents;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private BuildingEntity building;

    public RoomEntity addType(TypeEntity type) {
        types.add(type);
        type.getRooms().add(this);
        return this;
    }
}
