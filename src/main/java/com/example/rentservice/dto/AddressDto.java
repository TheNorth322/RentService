package com.example.rentservice.dto;

import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.AddressPartEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    @JsonProperty("value")
    private String name;
    private List<AddressPartDto> addressParts;

    public static AddressDto toDto(AddressEntity entity) {
        return AddressDto
                .builder()
                .name(entity.getName())
                .addressParts(entity.getAddressParts().stream().map(AddressPartDto::toDto).toList())
                .build();
    }
}
