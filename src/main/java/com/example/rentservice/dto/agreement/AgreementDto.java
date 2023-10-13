package com.example.rentservice.dto.agreement;

import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.enums.PaymentFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgreementDto {
    private Long registrationNumber;
    private PaymentFrequency paymentFrequency;
    private Integer fine;
    private Date startsFrom;
    private Date lastsTo;

    public static AgreementDto toDto(AgreementEntity entity) {
        return AgreementDto
                .builder()
                .fine(entity.getFine())
                .registrationNumber(entity.getRegistrationNumber())
                .paymentFrequency(entity.getPaymentFrequency())
                .startsFrom(entity.getStartsFrom())
                .lastsTo(entity.getLastsTo())
                .build();
    }
}
