package com.example.rentservice.dto.auth;

import com.example.rentservice.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private AddressDto address;
    private Long bankId;
    private String checkingAccount;
    private String itnNumber;
}
