package com.example.rentservice.service;

import com.example.rentservice.dto.room.*;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.TypeEntity;
import com.example.rentservice.exception.TypeExistsException;
import com.example.rentservice.exception.building.BuildingNotFoundException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.exception.room.RoomTypeNotFoundException;
import com.example.rentservice.repository.BuildingRepository;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public TypeDto createRoomType(CreateRoomTypeRequest request) throws TypeExistsException {
        Optional<TypeEntity> typeOptional = typeRepository.findByText(request.getText());
        if (typeOptional.isPresent())
            throw new TypeExistsException("Type already exists");

        return TypeDto.toDto(typeRepository.save(TypeEntity
                .builder()
                .text(request.getText())
                .build()
        ));
    }

    public String addRoomTypesToRoom(AddRoomTypesToRoomRequest request) throws RoomNotFoundException, TypeNotFoundException {
        RoomEntity room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        for (Long typeId: request.getTypesIds()) {
            TypeEntity type = typeRepository.findById(typeId).orElseThrow(() -> new TypeNotFoundException("Type not found"));
            room.addType(type);
        }

        roomRepository.save(room);
        return "Types were successfully added";
    }

    public List<TypeDto> getAllTypes() {
        return typeRepository.findAll().stream().map(TypeDto::toDto).toList();
    }

    public String updateRoomType(UpdateRoomTypeRequest request) throws RoomTypeNotFoundException {
        TypeEntity type = typeRepository.findById(request.getId()).orElseThrow(() -> new RoomTypeNotFoundException("Room type not found"));

        type.setText(request.getText());
        typeRepository.save(type);

        return "Type was updated successfully";
    }

    public String deleteRoomType(Long id) throws RoomTypeNotFoundException {
        TypeEntity type = typeRepository.findById(id).orElseThrow(() -> new RoomTypeNotFoundException("Room type not found"));

        typeRepository.delete(type);

        return "Type was deleted successfully";
    }
}
