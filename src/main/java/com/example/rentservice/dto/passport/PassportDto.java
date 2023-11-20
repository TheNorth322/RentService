package com.example.rentservice.dto.passport;

import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.entity.user.PassportEntity;
import com.example.rentservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {
    private String firstName;
    private String lastName;
    private String surname;
    private Date dateOfBirth;
    private Date dateOfIssue;
    private MigrationServiceDto issuedBy;
    private String number;
    private String series;
    private Gender gender;
    private String placeOfBirth;

    public static PassportDto toDto(PassportEntity entity) {
        return PassportDto
                .builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .surname(entity.getSurname())
                .dateOfBirth(entity.getDateOfBirth())
                .dateOfIssue(entity.getDateOfIssue())
                .issuedBy(MigrationServiceDto.toDto(entity.getMigrationService()))
                .number(entity.getNumber())
                .series(entity.getSeries())
                .gender(entity.getGender())
                .placeOfBirth(entity.getPlaceOfBirth())
                .build();
    }
}
