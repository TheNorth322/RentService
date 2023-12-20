package com.example.rentservice.dto.auth;

import com.example.rentservice.dto.AddressDto;
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
public class RegisterEntityRequest {
    private RegisterRequest registerRequest;
    private String name;
    private String supervisorFirstName;
    private String supervisorLastName;
    private String supervisorSurname;
    private String address;
    private List<AddressPartDto> addressParts;
    private Long bankId;
    private String checkingAccount;
    private String itnNumber;
}
