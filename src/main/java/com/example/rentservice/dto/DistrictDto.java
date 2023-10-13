package com.example.rentservice.dto;

import com.example.rentservice.entity.building.DistrictEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {
    private Long id;
    private String name;

    public static DistrictDto toDto(DistrictEntity entity) {
        return DistrictDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
