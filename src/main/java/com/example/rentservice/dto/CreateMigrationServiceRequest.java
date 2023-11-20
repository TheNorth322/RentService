package com.example.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMigrationServiceRequest {
    private String name;
    private String addressName;
    private String fiasId;
}
