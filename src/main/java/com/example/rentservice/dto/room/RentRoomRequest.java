package com.example.rentservice.dto.room;

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
public class RentRoomRequest {
    private Long userId;
    private List<Long> roomIds;
    private PaymentFrequency paymentFrequency;
    private Date startsFrom;
    private Date lastsTo;
}
