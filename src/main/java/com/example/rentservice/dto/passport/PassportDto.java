package com.example.rentservice.dto.passport;

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
    private String fullname;
    private Date dateOfBirth;
    private Date dateOfIssue;
    private String issuedBy;
    private Integer number;
    private Integer series;
    private Gender gender;
    private String placeOfBirth;

    public static PassportDto toDto(PassportEntity entity) {
        return PassportDto
                .builder()
                .fullname(entity.getFullname())
                .dateOfBirth(entity.getDateOfBirth())
                .dateOfIssue(entity.getDateOfIssue())
                .issuedBy(entity.getIssuedBy())
                .number(entity.getNumber())
                .series(entity.getSeries())
                .gender(entity.getGender())
                .placeOfBirth(entity.getPlaceOfBirth())
                .build();
    }
}
