package com.example.rentservice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddRoomTypesToRoomRequest {
    private Long roomId;
    private List<Long> typesIds;
}
