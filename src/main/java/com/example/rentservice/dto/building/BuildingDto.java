package com.example.rentservice.dto.building;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.building.BuildingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {
    private Long id;
    private String name;
    private AddressDto address;
    private Integer floorCount;
    private String telephone;

    public static BuildingDto toDto(BuildingEntity entity) {
        return BuildingDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(AddressDto.toDto(entity.getAddress()))
                .floorCount(entity.getFloorCount())
                .telephone(entity.getTelephone())
                .build();
    }
}
