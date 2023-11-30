package com.example.rentservice.dto.building;

import com.example.rentservice.dto.AddressPartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBuildingRequest {
    private String address;
    private List<AddressPartDto> addressParts;
    private Integer floorCount;
    private String telephone;
}
