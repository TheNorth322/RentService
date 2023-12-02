package com.example.rentservice.dto;

import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.service.MigrationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MigrationServiceDto {
    private Long id;

    private String name;
    private AddressDto address;

    public static MigrationServiceDto toDto(MigrationServiceEntity entity) {
        return MigrationServiceDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(AddressDto.toDto(entity.getAddress()))
                .build();
    }
}
