package com.example.rentservice.entity.room;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserRoomKey implements Serializable {
    @Column(name="user_id")
    private Long userId;

    @Column(name="room_id")
    private Long roomId;
}
