package com.example.rentservice.dto;

import com.example.rentservice.dto.passport.PassportDto;
import com.example.rentservice.dto.user.UserDto;
import com.example.rentservice.entity.user.IndividualUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualUserDto {
    private PassportDto activePassport;
    private List<PassportDto> passports;

    public static IndividualUserDto toDto(IndividualUserEntity entity) {
        return IndividualUserDto
                .builder()
                .activePassport(PassportDto.toDto(entity.getActivePassport()))
                .build();
    }
}
