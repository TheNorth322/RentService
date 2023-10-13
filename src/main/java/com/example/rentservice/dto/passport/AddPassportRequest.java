package com.example.rentservice.dto.passport;

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
public class AddPassportRequest {
    private String username;
    private String fullname;
    private Date dateOfBirth;
    private Date dateOfIssue;
    private String migrationServiceName;
    private Integer number;
    private Integer series;
    private Gender gender;
    private String placeOfBirth;
}
