package com.example.rentservice.dto.agreement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAgreementRoomDto {
    private Long roomId;
    private String purposeOfRent;
    private LocalDateTime startOfRent;
    private LocalDateTime endOfRent;
}
