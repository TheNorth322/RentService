package com.example.rentservice.entity.agreement;

import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "agreements")
public class AgreementEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "registration_number")
    private Long registrationNumber;

    @Column(name = "payment_frequency")
    @Enumerated(EnumType.STRING)
    private PaymentFrequency paymentFrequency;

    private Integer fine;

    @Column(name = "starts_from")
    private Date startsFrom;

    @Column(name = "lasts_to")
    private Date lastsTo;

    @Column(name = "additional_conditions")
    private String additionalConditions;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "agreement")
    private Set<AgreementRoomEntity> rents;
}
