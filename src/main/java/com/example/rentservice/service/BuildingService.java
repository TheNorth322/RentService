package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.dto.building.BuildingDto;
import com.example.rentservice.dto.building.CreateBuildingRequest;
import com.example.rentservice.dto.building.UpdateBuildingRequest;
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
                .name(request.getName())
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

    public String deleteBuilding(Long id) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building not found"));

        buildingRepository.delete(building);

        return "Building was successfully deleted";
    }

    public BuildingDto getBuilding(Long id) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
        return BuildingDto.toDto(building);
    }

    public String updateBuilding(UpdateBuildingRequest request) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository.findById(request.getId()).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
        AddressEntity address = addressService.createAddress(new CreateAddressRequest(request.getAddress(), request.getAddressParts()));

        building.setName(request.getName());
        building.setAddress(address);
        building.setTelephone(request.getTelephone());
        building.setFloorCount(request.getFloorCount());

        buildingRepository.save(building);
        return "Building was successfully updated";
    }

    public List<BuildingDto> getBuildings() {
        return buildingRepository.findAll().stream().map(BuildingDto::toDto).toList();
    }

    public BuildingDto getBuildingByAddress(AddressDto addressDto) throws AddressNotFoundException, BuildingNotFoundException {
        AddressEntity address = addressService.findAddress(addressDto);
        return BuildingDto.toDto(buildingRepository.findByAddress(address).orElseThrow(() -> new BuildingNotFoundException("Building not found")));
    }
}
