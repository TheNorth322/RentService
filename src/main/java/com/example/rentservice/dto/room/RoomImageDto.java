package com.example.rentservice.dto.room;

import com.example.rentservice.entity.room.RoomImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomImageDto {
    private Long id;
    private String url;

    public static RoomImageDto toDto(RoomImageEntity entity) {
        return RoomImageDto
                .builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .build();
    }
}
