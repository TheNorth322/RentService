package com.example.rentservice.service;

import com.example.rentservice.dto.room.CreateRoomRequest;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.exception.building.BuildingNotFoundException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.repository.BuildingRepository;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private RoomRepository roomRepository;

    public RoomDto createRoom(CreateRoomRequest request) throws TypeNotFoundException, BuildingNotFoundException {
        RoomEntity room = RoomEntity
                .builder()
                .building(buildingRepository
                        .findById(request.getBuildingId())
                        .orElseThrow(() -> new BuildingNotFoundException("Building not found"))
                )
                .area(request.getArea())
                .telephone(request.isTelephone())
                .number(request.getNumber())
                .floor(request.getFloor())
                .build();

        for (Long id: request.getTypesIds()) {
            room = room.addType(
                    typeRepository.findById(id).orElseThrow(() -> new TypeNotFoundException("Type not found"))
            );
        }

        roomRepository.save(room);

        return RoomDto.toDto(room);
    }

    public RoomDto getRoomById(Long id) throws RoomNotFoundException {
        return RoomDto.toDto(roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found")));
    }
}
