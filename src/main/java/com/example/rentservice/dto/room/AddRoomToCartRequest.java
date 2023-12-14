package com.example.rentservice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddRoomToCartRequest {
    private String username;
    private long roomId;
    private Date startOfRent;
    private Date endOfRent;
    private String purposeOfRent;
}
