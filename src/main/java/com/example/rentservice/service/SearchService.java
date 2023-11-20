package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.MigrationServiceDto;
import com.example.rentservice.dto.SearchAddressesRequest;
import com.example.rentservice.dto.building.BuildingSearchDto;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.TypeEntity;
import com.example.rentservice.exception.NoMigrationServicesFoundException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.building.NoBuildingsFoundException;
import com.example.rentservice.exception.room.NoRoomsFoundException;
import com.example.rentservice.repository.RoomRepository;
import com.example.rentservice.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private final String addressesUrl = "https://data.pbprog.ru/api/address/full-address/parse";
    private final String token = "8b183b3baa694aa4940afc9db53ba934ac44a52c";
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

    //TODO
    public List<AddressDto> searchAddresses(SearchAddressesRequest request) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(request.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(addressesUrl, request, String.class);

    }
}
