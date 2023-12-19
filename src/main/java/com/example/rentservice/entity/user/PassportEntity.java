package com.example.rentservice.entity.user;

import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"migrationService", "placeOfBirth", "user", "individualUser"})
@ToString(exclude = {"migrationService", "placeOfBirth", "user", "individualUser"})
@Table(name = "passports")
public class PassportEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "migration_service_id", nullable = false)
    private MigrationServiceEntity migrationService;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = false)
    private AddressEntity placeOfBirth;

    private String number;

    private String series;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "individual_user_id", nullable = false)
    private IndividualUserEntity user;

    @OneToOne(mappedBy = "activePassport")
    private IndividualUserEntity individualUser;
}
