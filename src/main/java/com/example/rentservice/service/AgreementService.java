package com.example.rentservice.service;

import com.example.rentservice.dto.agreement.CreateAgreementRequest;
import com.example.rentservice.entity.agreement.AgreementEntity;
import com.example.rentservice.entity.agreement.AgreementRoomEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.UserRoomEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.enums.PaymentFrequency;
import com.example.rentservice.exception.AgreementNotFoundException;
import com.example.rentservice.exception.UserRoomsNotFoundException;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.room.RoomNotFoundException;
import com.example.rentservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgreementService {
    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private PDFService pdfService;

    @Autowired
    private AgreementRoomRepository agreementRoomRepository;

    public String createAgreement(CreateAgreementRequest request) throws UserNotFoundException, UserRoomsNotFoundException, RoomNotFoundException {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<UserRoomEntity> userRooms = userRoomRepository.findById_UserId(user.getId());

        if (userRooms.isEmpty())
            throw new UserRoomsNotFoundException("User rooms not found");

        AgreementEntity agreement = agreementRepository.save(AgreementEntity
                .builder()
                .user(user)
                .registrationNumber(generateRegistrationNumber())
                .paymentFrequency(request.getPaymentFrequency())
                .additionalConditions(request.getAdditionalConditions())
                .fine(getFine(userRooms))
                .startsFrom(getStartsFrom(user.getId()))
                .lastsTo(getLastsTo(user.getId()))
                .build());

        for (UserRoomEntity userRoom: userRooms) {
            createAgreementRoom(userRoom, agreement);
            userRoomRepository.delete(userRoom);
        }

        return "Agreement was successfully created";
    }

    private AgreementRoomEntity createAgreementRoom(UserRoomEntity userRoom, AgreementEntity agreement) throws RoomNotFoundException {
        RoomEntity room = roomRepository.findById(userRoom.getId().getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        int roomPrice = room.getPrice();
        PaymentFrequency paymentFrequency = agreement.getPaymentFrequency();

        long monthsOfRent = getMonthsDifference(userRoom.getStartOfRent(), userRoom.getEndOfRent());

        int rentAmount;
        if (paymentFrequency == PaymentFrequency.MONTHLY) {
            rentAmount = (int) (roomPrice * monthsOfRent);
        } else if (paymentFrequency == PaymentFrequency.QUARTERLY) {
            long quartersOfRent = monthsOfRent / 3;
            rentAmount = (int) (roomPrice * quartersOfRent);
        } else {
            rentAmount = 0;
        }

        return agreementRoomRepository.save(AgreementRoomEntity
                .builder()
                        .room(room)
                        .agreement(agreement)
                        .purposeOfRent(userRoom.getPurposeOfRent())
                        .startOfRent(userRoom.getStartOfRent())
                        .endOfRent(userRoom.getEndOfRent())
                        .rentAmount(rentAmount)
                .build());


    }

    private int getFine(List<UserRoomEntity> userRooms) throws RoomNotFoundException {
        int fine = 0;

        for (UserRoomEntity userRoom: userRooms) {
            fine += roomRepository.findById(userRoom.getId().getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException("Room not found"))
                    .getFine();
        }

        return fine;
    }
    private Long generateRegistrationNumber() {
        Optional<AgreementEntity> agreement = agreementRepository.findTopByOrderByRegistrationNumberDesc();
        return agreement.map(agreementEntity -> agreementEntity.getRegistrationNumber() + 1).orElse(1L);
    }

    private Date getStartsFrom(Long userId) throws UserRoomsNotFoundException {
        return userRoomRepository.findFirstById_UserIdOrderByStartOfRentAsc(userId).orElseThrow(() -> new UserRoomsNotFoundException("User room not found")).getStartOfRent();
    }

    private Date getLastsTo(Long userId) throws UserRoomsNotFoundException {
        return userRoomRepository.findFirstById_UserIdOrderByEndOfRentDesc(userId).orElseThrow(() -> new UserRoomsNotFoundException("User room not found")).getEndOfRent();
    }

    private long getMonthsDifference(Date startDate, Date endDate) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH);

        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH);

        return (endYear - startYear) * 12L + (endMonth - startMonth);
    }

    public byte[] generateAgreementPdf(Long id) throws AgreementNotFoundException, IOException {
        AgreementEntity agreement = agreementRepository.findById(id).orElseThrow(() -> new AgreementNotFoundException("Agreement not found"));
        return pdfService.generateAgreementPdf(agreement);
    }
}
