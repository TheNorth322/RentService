package com.example.rentservice.service;

import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.exception.MigrationServiceExistsException;
import com.example.rentservice.exception.NoMigrationServicesFoundException;
import com.example.rentservice.repository.MigrationServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MigrationService {
    @Autowired
    private MigrationServiceRepository migrationServiceRepository;

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
    public MigrationServiceDto createMigrationService(String name) throws MigrationServiceExistsException {
        if (migrationServiceRepository.findByName(name).isPresent()) {
            throw new MigrationServiceExistsException("Migration service already exists");
        }

        return MigrationServiceDto.toDto(migrationServiceRepository.save(
                MigrationServiceEntity
                        .builder()
                        .name(name)
                        .build()
        ));
    }
}
