package com.example.rentservice.entity.room;

import com.example.rentservice.entity.room.RoomEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "types")
public class TypeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToMany(mappedBy = "types")
    private Set<RoomEntity> rooms;
}
