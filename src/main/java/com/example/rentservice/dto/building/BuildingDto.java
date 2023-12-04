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
    private AddressDto address;
    private Integer floorCount;
    private String telephone;
    private List<RoomDto> rooms;

    public static BuildingDto toDto(BuildingEntity entity) {
        return BuildingDto
                .builder()
                .address(AddressDto.toDto(entity.getAddress()))
                .floorCount(entity.getFloorCount())
                .telephone(entity.getTelephone())
                .rooms(entity.getRooms().stream().map(RoomDto::toDto).toList())
                .build();
    }
}
