package com.example.rentservice.dto.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBuildingRequest {
    private String address;
    private String fiasId;
    private Integer floorCount;
    private String telephone;
}
