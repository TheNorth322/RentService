package com.example.rentservice.dto.bank;

import com.example.rentservice.entity.BankEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
    private long id;
    private String name;

    public static BankDto toDto(BankEntity entity) {
        return BankDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
