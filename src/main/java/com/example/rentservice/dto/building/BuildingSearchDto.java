package com.example.rentservice.dto.building;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.entity.building.BuildingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingSearchDto {
    private Long id;
    private AddressDto address;
    private String telephone;

    public static BuildingSearchDto toDto(BuildingEntity entity) {
        return BuildingSearchDto
                .builder()
                .id(entity.getId())
                .address(AddressDto.toDto(entity.getAddress()))
                .telephone(entity.getTelephone())
                .build();
    }
}
