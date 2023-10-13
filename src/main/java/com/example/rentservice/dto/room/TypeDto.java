package com.example.rentservice.dto.room;

import com.example.rentservice.entity.room.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
    private Long id;
    private String text;

    public static TypeDto toDto(TypeEntity entity) {
        return TypeDto
                .builder()
                .id(entity.getId())
                .text(entity.getText())
                .build();
    }
}
