package com.example.rentservice.dto.migrationService;

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
public class UpdateMigrationServiceRequest {
    private Long id;
    private String name;
    private String addressName;
    private List<AddressPartDto> addressParts;
}
