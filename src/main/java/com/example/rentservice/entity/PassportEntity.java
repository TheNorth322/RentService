package com.example.rentservice.entity;

import com.example.rentservice.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "passports")
public class PassportEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String fullname;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "date_of_issue")
    private LocalDateTime dateOfIssue;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    private Integer number;

    private Integer series;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private IndividualUserEntity user;
}
