package com.example.rentservice.entity.user;

import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date dateOfBirth;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "migration_service_id", nullable = false)
    private MigrationServiceEntity migrationService;

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
