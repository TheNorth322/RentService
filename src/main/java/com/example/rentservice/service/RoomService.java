package com.example.rentservice.service;

import com.example.rentservice.dto.room.*;
import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.room.*;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.TypeExistsException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.UserRoomAlreadyExistsException;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.building.BuildingNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.exception.room.RoomTypeNotFoundException;
import com.example.rentservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(RoomDto::toDto).toList();
    }

    public RoomDto createRoom(CreateRoomRequest request) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository.findById(request.getBuildingId()).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
        Set<TypeEntity> types = request.getTypes().stream().map(this::getType).collect(Collectors.toSet());

        RoomEntity room = roomRepository.save(RoomEntity
                .builder()
                .building(building)
                .telephone(request.isTelephone())
                .area(request.getArea())
                .number(request.getNumber())
                .floor(request.getFloor())
                .price(request.getPrice())
                .description(request.getDescription())
                .types(types)
                .build());

        Set<RoomImageEntity> roomImages = request.getRoomImages().stream().map((roomImage) -> {return getRoomImage(roomImage, room);}).collect(Collectors.toSet());
        room.setRoomImages(roomImages);
        return RoomDto.toDto(roomRepository.save(room));
    }

    private RoomImageEntity getRoomImage(RoomImageDto roomImageDto, RoomEntity room) {
        return roomImageRepository.findByUrl(roomImageDto.getUrl()).orElseGet(() -> roomImageRepository.save(
                RoomImageEntity
                        .builder()
                        .room(room)
                        .url(roomImageDto.getUrl())
                        .build()
        ));
    }

    private TypeEntity getType(TypeDto typeDto) {
        return typeRepository.findById(typeDto.getId()).orElseGet(() -> typeRepository.save(TypeEntity
                .builder()
                .text(typeDto.getText())
                .build())
        );
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

        for (Long typeId : request.getTypesIds()) {
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

    public String updateRoom(UpdateRoomRequest request) throws BuildingNotFoundException, RoomNotFoundException {
        BuildingEntity building = buildingRepository.findById(request.getBuildingId()).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
        Set<TypeEntity> types = request.getTypes().stream().map(this::getType).collect(Collectors.toSet());

        RoomEntity room = roomRepository.findById(request.getId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));
        Set<RoomImageEntity> roomImages = request.getRoomImages().stream().map((roomImage) -> {return getRoomImage(roomImage, room);}).collect(Collectors.toSet());

        room.setBuilding(building);
        room.setTelephone(request.isTelephone());
        room.setArea(request.getArea());
        room.setNumber(request.getNumber());
        room.setFloor(request.getFloor());
        room.setPrice(request.getPrice());
        room.setDescription(request.getDescription());
        room.setTypes(types);
        room.setRoomImages(roomImages);

        roomRepository.save(room);

        return "Room was successfully updated";
    }

    public String deleteRoom(long id) throws RoomNotFoundException {
        RoomEntity room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found"));
        roomRepository.delete(room);
        return "Room was successfully deleted";
    }


    public String addRoomToCart(AddRoomToCartRequest request) throws UserNotFoundException, RoomNotFoundException, UserRoomAlreadyExistsException {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        RoomEntity room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));
        UserRoomKey id = UserRoomKey
                .builder()
                .userId(user.getId())
                .roomId(room.getId())
                .build();

        Optional<UserRoomEntity> userRoomOptional = userRoomRepository.findById(id);

        if (userRoomOptional.isPresent())
            throw new UserRoomAlreadyExistsException("Room is already in cart");

        UserRoomEntity userRoom = UserRoomEntity
                .builder()
                .id(id)
                .startOfRent(request.getStartOfRent())
                .endOfRent(request.getEndOfRent())
                .purposeOfRent(request.getPurposeOfRent())
                .build();

        userRoomRepository.save(userRoom);
        return "Room was successfully added to cart";
    }
}
