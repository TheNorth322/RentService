package com.example.rentservice.entity.agreement;

import com.example.rentservice.entity.room.RoomEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "agreement_room")
public class AgreementRoomEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agreement_id", nullable = false)
    private AgreementEntity agreement;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Column(name = "purpose_of_rent")
    private String purposeOfRent;

    @Column(name = "start_of_rent")
    private Date startOfRent;

    @Column(name = "end_of_rent")
    private Date endOfRent;

    @Column(name = "rent_amount")
    private Integer rentAmount;
}
