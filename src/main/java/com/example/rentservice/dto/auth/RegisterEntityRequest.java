package com.example.rentservice.dto.auth;

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
    private String address;
    private Long bankId;
    private String checkingAccount;
    private String itnNumber;
}
