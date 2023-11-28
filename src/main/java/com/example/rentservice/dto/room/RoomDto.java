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
    private AddressDto address;
    private Integer number;
    private Integer floor;
    private List<TypeDto> types;
    private String description;
    private Integer price;
    private BuildingDto building;
    private List<RoomImageDto> images;

    public static RoomDto toDto(RoomEntity entity) {
        return RoomDto
                .builder()
                .id(entity.getId())
                .isTelephone(entity.isTelephone())
                .address(AddressDto.toDto(entity.getBuilding().getAddress()))
                .number(entity.getNumber())
                .floor(entity.getFloor())
                .area(entity.getArea())
                .price(entity.getPrice())
                .types(entity.getTypes().stream().map(TypeDto::toDto).toList())
                .description(entity.getDescription())
                .building(BuildingDto.toDto(entity.getBuilding()))
                .build();
    }
}
