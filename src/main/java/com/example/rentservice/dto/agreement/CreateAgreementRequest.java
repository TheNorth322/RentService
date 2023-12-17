package com.example.rentservice.dto.agreement;

import com.example.rentservice.enums.PaymentFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAgreementRequest {
    private String username;
    private PaymentFrequency paymentFrequency;
    private String additionalConditions;
}
