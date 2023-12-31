package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.dto.CreateMigrationServiceRequest;
import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.dto.migrationService.UpdateMigrationServiceRequest;
import com.example.rentservice.dto.passport.PassportDto;
import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.exception.*;
import com.example.rentservice.repository.MigrationServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MigrationService {
    @Autowired
    private MigrationServiceRepository migrationServiceRepository;

    @Autowired
    private AddressService addressService;

    public List<MigrationServiceDto> getMigrationServices() {
        return migrationServiceRepository.findAll().stream().map(MigrationServiceDto::toDto).toList();
    }

    public List<MigrationServiceDto> getMigrationServicesByName(String name) throws NoMigrationServicesFoundException {
        List<MigrationServiceEntity> migrationServices = migrationServiceRepository.findAllByNameLike(name);

        if (migrationServices.isEmpty())
            throw new NoMigrationServicesFoundException("No migration services found");

        return migrationServices
                .stream()
                .map(MigrationServiceDto::toDto)
                .toList();
    }
    public MigrationServiceDto createMigrationService(CreateMigrationServiceRequest request) throws MigrationServiceExistsException, AddressNotFoundException {

        if (migrationServiceRepository.findByName(request.getName()).isPresent()) {
            throw new MigrationServiceExistsException("Migration service already exists");
        }

        AddressEntity address = addressService.createAddress(
                    CreateAddressRequest.builder()
                            .name(request.getAddressName())
                            .addressParts(request.getAddressParts())
                            .build()
            );

        return MigrationServiceDto.toDto(migrationServiceRepository.save(
                MigrationServiceEntity.builder()
                        .name(request.getName())
                        .address(address)
                        .build())
        );
    }

    public String updateMigrationService(UpdateMigrationServiceRequest request) throws MigrationServiceNotFoundException, AddressNotFoundException {
        MigrationServiceEntity migrationService = migrationServiceRepository.findById(request.getId()).orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));
        AddressEntity address = addressService.createAddress(new CreateAddressRequest(request.getAddressName(), request.getAddressParts()));

        migrationService.setName(request.getName());
        migrationService.setAddress(address);

        migrationServiceRepository.save(migrationService);

        return "Migration service was successfully updated";
    }

    public String deleteMigrationService(long id) throws MigrationServiceNotFoundException {
        MigrationServiceEntity migrationService = migrationServiceRepository.findById(id).orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));
        migrationServiceRepository.delete(migrationService);
        return "Migration service deleted successfully";
    }

    public List<PassportDto> getMigrationServicePassports(Long id) throws MigrationServiceNotFoundException {
        MigrationServiceEntity migrationService = migrationServiceRepository.findById(id).orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));
        return migrationService.getPassports().stream().map(PassportDto::toDto).toList();
    }
}
