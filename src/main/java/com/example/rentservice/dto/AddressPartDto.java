package com.example.rentservice.dto;

import com.example.rentservice.entity.AddressPartEntity;
import com.example.rentservice.enums.AddressLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressPartDto {
    private String objectGuid;
    private String name;
    private String typeName;
    private String fullTypeName;
    private AddressLevel level;

    public static AddressPartDto toDto(AddressPartEntity entity) {
        return AddressPartDto
                .builder()
                .objectGuid(entity.getObjectGuid())
                .name(entity.getName())
                .typeName(entity.getTypeName())
                .fullTypeName(entity.getFullTypeName())
                .level(entity.getLevel())
                .build();
    }
}
