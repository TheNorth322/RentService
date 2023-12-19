package com.example.rentservice.dto.agreement;

import com.example.rentservice.dto.room.AgreementRoomDto;
import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.agreement.AgreementRoomEntity;
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
public class AgreementDto {
    private Long id;
    private Long registrationNumber;
    private PaymentFrequency paymentFrequency;
    private String additionalConditions;
    private Integer fine;
    private Date startsFrom;
    private Date lastsTo;

    public static AgreementDto toDto(AgreementEntity entity) {
        return AgreementDto
                .builder()
                .id(entity.getId())
                .additionalConditions(entity.getAdditionalConditions())
                .fine(entity.getFine())
                .registrationNumber(entity.getRegistrationNumber())
                .paymentFrequency(entity.getPaymentFrequency())
                .startsFrom(entity.getStartsFrom())
                .lastsTo(entity.getLastsTo())
                .build();
    }
}
