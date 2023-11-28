package com.example.rentservice.dto.agreement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAgreementRoomDto {
    private Long roomId;
    private String purposeOfRent;
    private Date startOfRent;
    private Date endOfRent;
}
