package com.example.rentservice.dto.building;

import com.example.rentservice.dto.AddressPartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBuildingRequest {
    private Long id;
    private String address;
    private List<AddressPartDto> addressParts;
    private int floorCount;
    private String telephone;
}
