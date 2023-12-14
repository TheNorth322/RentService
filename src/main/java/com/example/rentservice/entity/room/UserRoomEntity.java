package com.example.rentservice.entity.room;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_rooms")
public class UserRoomEntity {
    @EmbeddedId
    private UserRoomKey id;

    @Column(name="purpose_of_rent")
    private String purposeOfRent;

    @Column(name="start_of_rent")
    private Date startOfRent;

    @Column(name="end_of_rent")
    private Date endOfRent;
}
