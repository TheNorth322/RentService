package com.example.rentservice.entity;

import com.example.rentservice.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private PaymentFrequency paymentFrequency;

    private Integer fine;

    @Column(name = "starts_from")
    private LocalDateTime startsFrom;

    @Column(name = "lasts_to")
    private LocalDateTime lastsTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "user")
    private Set<AgreementRoomEntity> rents;
}
