package com.example.rentservice.dto.room;

import com.example.rentservice.entity.room.UserRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomDto {
    private long roomId;
    private Date startOfRent;
    private Date endOfRent;

    public static UserRoomDto toDto(UserRoomEntity entity) {
        return UserRoomDto
                .builder()
                .roomId(entity.getId().getRoomId())
                .startOfRent(entity.getStartOfRent())
                .endOfRent(entity.getEndOfRent())
                .build();
    }
}
