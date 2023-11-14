package com.example.rentservice.service;

import com.example.rentservice.dto.agreement.CreateAgreementRequest;
import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.repository.AgreementRepository;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgreementService {
    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public String createAgreement(CreateAgreementRequest request) throws UserNotFoundException {
        UserEntity user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<AgreementRoomEntity> rooms = request.getAgreementRooms()
                .stream()
                .map((room) -> {
                    try {
                        RoomEntity roomEntity = roomRepository.findById(room.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"))
                        return AgreementRoomEntity
                                .builder()
                                .room(roomEntity)
                                .startOfRent(room.getStartOfRent())
                                .endOfRent(room.getEndOfRent())
                                .purposeOfRent(room.getPurposeOfRent())
                                .rentAmount()
                                .build();
                    } catch (RoomNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        AgreementEntity agreement = AgreementEntity
                .builder()
                .registrationNumber(generateRegistrationNumber())
                .additionalConditions(request.getAdditionalConditions())
                .paymentFrequency(request.getPaymentFrequency())
                .fine(request.getFine())
                .rents(rooms)
                .startsFrom(request.getStartsFrom())
                .lastsTo(request.getLastsTo())
                .build();

        userRepository.save(user.addAgreement(agreement));

        return "Agreement was successfully created";
    }

    private Long generateRegistrationNumber() {
        Optional<AgreementEntity> agreement = agreementRepository.findTopByRegistrationNumber();
        return agreement.map(agreementEntity -> agreementEntity.getRegistrationNumber() + 1).orElse(1L);
    }
}
