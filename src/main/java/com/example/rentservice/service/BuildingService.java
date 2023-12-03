package com.example.rentservice.service;

import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.dto.building.BuildingDto;
import com.example.rentservice.dto.building.CreateBuildingRequest;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.exception.AddressNotFoundException;
import com.example.rentservice.exception.BuildingAlreadyExistsException;
import com.example.rentservice.exception.building.BuildingNotFoundException;
import com.example.rentservice.exception.building.NoBuildingsFoundException;
import com.example.rentservice.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private AddressService addressService;

    public BuildingDto createBuilding(CreateBuildingRequest request) throws AddressNotFoundException, BuildingAlreadyExistsException {
        AddressEntity address = addressService.createAddress(new CreateAddressRequest(request.getAddress(), request.getAddressParts()));

        if (buildingRepository.findByAddress(address).isPresent())
            throw new BuildingAlreadyExistsException("Building already exists");

        BuildingEntity building = BuildingEntity
                .builder()
                .address(address)
                .floorCount(request.getFloorCount())
                .telephone(request.getTelephone())
                .build();

        return BuildingDto.toDto(buildingRepository.save(building));
    }

    public List<RoomDto> getRooms(Long id) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository
                .findById(id)
                .orElseThrow(() -> new BuildingNotFoundException("Building not found"));

        return building
                .getRooms()
                .stream()
                .map(RoomDto::toDto)
                .toList();
    }

    public List<BuildingEntity> findBuildingsByAddress(String address) throws NoBuildingsFoundException {
        List<BuildingEntity> buildings = buildingRepository.findAllByAddressLike(address);
        if (buildings.isEmpty())
            throw new NoBuildingsFoundException("No buildings found");
        return buildings;
    }
}
