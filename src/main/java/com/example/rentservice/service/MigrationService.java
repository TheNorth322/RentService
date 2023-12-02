package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.dto.CreateMigrationServiceRequest;
import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.dto.migrationService.UpdateMigrationServiceRequest;
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
    public String createMigrationService(CreateMigrationServiceRequest request) throws MigrationServiceExistsException, AddressNotFoundException {

        if (migrationServiceRepository.findByName(request.getName()).isPresent()) {
            throw new MigrationServiceExistsException("Migration service already exists");
        }
        try {
            AddressEntity address = addressService.createAddress(
                    CreateAddressRequest
                            .builder()
                            .name(request.getName())
                            .addressParts(request.getAddressParts())
                            .build()
            );
            migrationServiceRepository.save(
                    MigrationServiceEntity
                            .builder()
                            .name(request.getName())
                            .address(address)
                            .build()

            );
            return "Migration service create successfully";
        } catch (AddressAlreadyExistsException e) {
            AddressEntity address = addressService.findByName(request.getName(), request.getAddressParts());
            migrationServiceRepository.save(
                    MigrationServiceEntity
                            .builder()
                            .name(request.getName())
                            .address(address)
                            .build());
            return "Migration service create successfully";
        }
    }

    public String updateMigrationService(UpdateMigrationServiceRequest request) throws MigrationServiceNotFoundException, AddressNotFoundException {
        MigrationServiceEntity migrationService = migrationServiceRepository.findById(request.getId()).orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));
        AddressEntity address = addressService.findByName(request.getAddressName(), request.getAddressParts());

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
}
