package com.example.rentservice.service;

import aj.org.objectweb.asm.TypeReference;
import com.example.rentservice.dto.*;
import com.example.rentservice.dto.building.BuildingDto;
import com.example.rentservice.dto.building.BuildingSearchDto;
import com.example.rentservice.dto.room.RoomDto;
import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.AddressPartEntity;
import com.example.rentservice.entity.building.BuildingEntity;
import com.example.rentservice.entity.room.RoomEntity;
import com.example.rentservice.entity.room.TypeEntity;
import com.example.rentservice.entity.user.MigrationServiceEntity;
import com.example.rentservice.exception.AddressNotFoundException;
import com.example.rentservice.exception.MigrationServiceNotFoundException;
import com.example.rentservice.exception.NoMigrationServicesFoundException;
import com.example.rentservice.exception.TypeNotFoundException;
import com.example.rentservice.exception.building.BuildingNotFoundException;
import com.example.rentservice.exception.building.NoBuildingsFoundException;
import com.example.rentservice.exception.room.NoRoomsFoundException;
import com.example.rentservice.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private MigrationService migrationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private IndividualUserRepository individualUserRepository;

    @Autowired
    private MigrationServiceRepository migrationServiceRepository;

    @Autowired
    private BuildingRepository buildingRepository;
    private final String addressesUrl = "https://data.pbprog.ru/api/address/full-address/parse";
    private final String token = "9c7f09abadcc430493fb7b81ea22fd9e76aba61e";

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

    public AddressDto[] searchAddresses(String query, int count) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?token=%s&addressText=%s&count=%d", addressesUrl, token, query, count);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        return parseAddresses(response.getBody());
    }

    private AddressDto[] parseAddresses(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, AddressDto[].class);
    }

    public List<MigrationServiceDto> searchMigrationServices() {
        return migrationService.getMigrationServices();
    }

    public BuildingDto getBuildingByAddress(AddressDto addressDto) throws AddressNotFoundException, BuildingNotFoundException {
        return buildingService.getBuildingByAddress(addressDto);
    }

    public List<RoomDto> findAvailableRoomsInBuilding(Long id) throws BuildingNotFoundException {
        BuildingEntity building = buildingRepository.findById(id).orElseThrow(() -> new BuildingNotFoundException("Building not found"));
        return roomRepository.findAvailableRoomsInBuilding(building).stream().map(RoomDto::toDto).toList();
    }

    public List<RoomDto> findRoomsInBuildingsWithRentals() {
        return roomRepository.findRoomsInBuildingsWithRentals().stream().map(RoomDto::toDto).toList();
    }

    public List<IndividualUserDto> findAllByPassportMigrationService(Long id) throws MigrationServiceNotFoundException {
        MigrationServiceEntity migrationService = migrationServiceRepository.findById(id).orElseThrow(() -> new MigrationServiceNotFoundException("Migration service not found"));
        return individualUserRepository.findAllByPassportMigrationService(migrationService).stream().map(IndividualUserDto::toDto).toList();
    }
}
