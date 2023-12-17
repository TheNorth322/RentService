package com.example.rentservice.dto.room;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.building.BuildingDto;
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
    private long id;
    private boolean isTelephone;
    private Double area;
    private Integer number;
    private Integer floor;
    private List<TypeDto> types;
    private String description;
    private Integer price;
    private BuildingDto building;
    private Integer fine;
    private List<RoomImageDto> images;

    public static RoomDto toDto(RoomEntity entity) {
        return RoomDto
                .builder()
                .id(entity.getId())
                .isTelephone(entity.isTelephone())
                .number(entity.getNumber())
                .floor(entity.getFloor())
                .area(entity.getArea())
                .price(entity.getPrice())
                .types(entity.getTypes().stream().map(TypeDto::toDto).toList())
                .description(entity.getDescription())
                .building(BuildingDto.toDto(entity.getBuilding()))
                .images(entity.getRoomImages().stream().map(RoomImageDto::toDto).toList())
                .fine(entity.getFine())
                .build();
    }
}
