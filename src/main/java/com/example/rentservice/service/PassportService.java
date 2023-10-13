package com.example.rentservice.service;

import com.example.rentservice.dto.passport.AddPassportRequest;
import com.example.rentservice.dto.passport.PassportDto;
import com.example.rentservice.entity.user.IndividualUserEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.entity.user.PassportEntity;
import com.example.rentservice.exception.auth.IndividualUserNotFoundException;
import com.example.rentservice.exception.passport.PassportNotFoundException;
import com.example.rentservice.exception.auth.UserNotFoundException;
import com.example.rentservice.repository.IndividualUserRepository;
import com.example.rentservice.repository.MigrationServiceRepository;
import com.example.rentservice.repository.PassportRepository;
import com.example.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassportService {
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

    public IndividualUserEntity addPassport(AddPassportRequest request) throws UserNotFoundException {
        validateRequest(request);
        IndividualUserEntity user = individualUserRepository.findByUser_Username(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<MigrationServiceEntity> migrationServiceOptional = migrationServiceRepository.findByName(request.getMigrationServiceName());
        MigrationServiceEntity migrationService = migrationServiceOptional.orElseGet(() -> MigrationServiceEntity.builder().name(request.getMigrationServiceName()).build());;

        PassportEntity passport = PassportEntity
                .builder()
                .user(user)
                .dateOfBirth(request.getDateOfBirth())
                .dateOfIssue(request.getDateOfIssue())
                .fullname(request.getFullname())
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
}
