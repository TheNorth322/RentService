package com.example.rentservice.service;

import com.example.rentservice.dto.IndividualUserDto;
import com.example.rentservice.dto.passport.AddPassportRequest;
import com.example.rentservice.dto.passport.PassportDto;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.entity.user.PassportEntity;
import com.example.rentservice.entity.user.UserEntity;
import com.example.rentservice.exception.MigrationServiceNotFoundException;
import com.example.rentservice.exception.auth.IndividualUserNotFoundException;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.exception.passport.PassportNotFoundException;
import com.example.rentservice.repository.IndividualUserRepository;
import com.example.rentservice.repository.MigrationServiceRepository;
import com.example.rentservice.repository.PassportRepository;
import com.example.rentservice.repository.UserRepository;
import lombok.SneakyThrows;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassportService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private IndividualUserRepository individualUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private MigrationServiceRepository migrationServiceRepository;

    public List<PassportDto> getUserPassport(String username) throws UserNotFoundException, IndividualUserNotFoundException {
        return individualUserRepository
                .findByUser_Username(username)
                .orElseThrow(() -> new IndividualUserNotFoundException("Individual not found"))
                .getPassports()
                .stream()
                .map(PassportDto::toDto)
                .toList();
    }

    public IndividualUserEntity deletePassport(String username, Long passportId) throws PassportNotFoundException {
        PassportEntity passport = passportRepository
                .findById(passportId)
                .orElseThrow(() -> new PassportNotFoundException("Passport not found"));

        IndividualUserEntity user = passport.getUser();

        user.deletePassport(passport);
        individualUserRepository.save(user);
        passportRepository.delete(passport);

        return user;
    }

    public IndividualUserEntity addPassport(AddPassportRequest request) throws UserNotFoundException, MigrationServiceNotFoundException {
        validateRequest(request);
        IndividualUserEntity user = individualUserRepository.findByUser_Username(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        MigrationServiceEntity migrationService = migrationServiceRepository.findById(request.getMigrationServiceId())
                .orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));

        PassportEntity passport = PassportEntity
                .builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .surname(request.getSurname())
                .dateOfBirth(request.getDateOfBirth())
                .dateOfIssue(request.getDateOfIssue())
                .gender(request.getGender())
                .number(request.getNumber())
                .series(request.getSeries())
                .placeOfBirth(request.getPlaceOfBirth())
                .migrationService(migrationService)
                .build();

        migrationServiceRepository.save(migrationService.addPassport(passport));

        return individualUserRepository.save(user.addPassport(passport));
    }

    private void validateRequest(AddPassportRequest request) {
        //TODO
    }

    public String setActivePassport(String token, Long passportId) throws IndividualUserNotFoundException, PassportNotFoundException {
        String username = jwtService.extractUsername(token);
        IndividualUserEntity individualUser = individualUserRepository.findByUser_Username(username).orElseThrow(() -> new IndividualUserNotFoundException("Individual user not found"));
        PassportEntity passport = passportRepository.findById(passportId).orElseThrow(() -> new PassportNotFoundException("Passport not found"));

        individualUser.setActivePassport(passport);

        individualUserRepository.save(individualUser);

        return "Passport was changed successfully";
    }

    public IndividualUserDto getUserIndividualInfo(String username) throws UserNotFoundException, IndividualUserNotFoundException {
        IndividualUserEntity user = individualUserRepository.findByUser_Username(username).orElseThrow(() -> new IndividualUserNotFoundException("Individual user not found"));
        List<PassportDto> passports = getUserPassport(username);

        return IndividualUserDto
                .builder()
                .passports(passports)
                .activePassport(PassportDto.toDto(user.getActivePassport()))
                .build();
    }
}
