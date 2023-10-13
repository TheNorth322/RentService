package com.example.rentservice.dto.room;

import com.example.rentservice.entity.room.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private boolean isTelephone;
    private Double area;
    private String address;
    private String district;
    private Integer number;
    private Integer floor;
    private List<TypeDto> types;
    private String description;
    public static RoomDto toDto(RoomEntity entity) {
        return RoomDto
                .builder()
                .isTelephone(entity.isTelephone())
                .address(entity.getBuilding().getAddress())
                .district(entity.getBuilding().getDistrict())
                .number(entity.getNumber())
                .floor(entity.getFloor())
                .area(entity.getArea())
                .types(entity.getTypes().stream().map(TypeDto::toDto).toList())
                .description(entity.getDescription())
                .build();
    }
}
