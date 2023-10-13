package com.example.rentservice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    private Long buildingId;
    private boolean telephone;
    private Double area;
    private Integer number;
    private Integer floor;
    private Set<Long> typesIds;
}
