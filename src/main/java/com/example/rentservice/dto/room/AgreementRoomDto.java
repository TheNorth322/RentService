package com.example.rentservice.dto.room;

import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgreementRoomDto {
    private Long id;
    private RoomDto room;
    private String purposeOfRent;
    private Date startOfRent;
    private Date endOfRent;
    private Integer rentAmount;

    public static AgreementRoomDto toDto(AgreementRoomEntity entity) {
        return AgreementRoomDto
                .builder()
                .id(entity.getId())
                .room(RoomDto.toDto(entity.getRoom()))
                .purposeOfRent(entity.getPurposeOfRent())
                .startOfRent(entity.getStartOfRent())
                .endOfRent(entity.getEndOfRent())
                .rentAmount(entity.getRentAmount())
                .build();
    }
}
