package com.example.rentservice.dto.auth;

import com.example.rentservice.dto.passport.AddPassportRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterIndividualRequest {
    private RegisterRequest registerRequest;
    private AddPassportRequest addPassportRequest;
}
