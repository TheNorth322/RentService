package com.example.rentservice.service;

import com.example.rentservice.dto.DistrictDto;
import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.dto.building.BuildingSearchDto;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.TypeEntity;
import com.example.rentservice.exception.NoDistrictsFoundException;
import com.example.rentservice.exception.NoMigrationServicesFoundException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.building.NoBuildingsFoundException;
import com.example.rentservice.exception.room.NoRoomsFoundException;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private MigrationService migrationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TypeRepository typeRepository;

    public List<BuildingSearchDto> searchBuildingsByAddress(String address) throws NoBuildingsFoundException {
        return buildingService.findBuildingsByAddress(address)
                .stream()
                .map(BuildingSearchDto::toDto)
                .toList();
    }


    public List<RoomDto> searchRoomsByType(Long typeId) throws NoRoomsFoundException, TypeNotFoundException {
        TypeEntity type = typeRepository.findById(typeId).orElseThrow(() -> new TypeNotFoundException("Type not found"));
        List<RoomEntity> rooms =  roomRepository.findAllByTypesContains(type);

        if (rooms.isEmpty())
            throw new NoRoomsFoundException("No rooms found");

        return rooms
                .stream()
                .map(RoomDto::toDto)
                .toList();
    }

    public List<MigrationServiceDto> searchMigrationServicesByName(String name) throws NoMigrationServicesFoundException {
        return migrationService.getMigrationServicesByName(name);
    }

    public List<DistrictDto> searchDistrictsByName(String name) throws NoDistrictsFoundException {
        return districtService.getAllDistrictsByName(name);
    }
}
