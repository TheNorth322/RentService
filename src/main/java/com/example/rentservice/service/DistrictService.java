package com.example.rentservice.service;

import com.example.rentservice.dto.DistrictDto;
import com.example.rentservice.entity.building.DistrictEntity;
import com.example.rentservice.exception.DistrictAlreadyExistsException;
import com.example.rentservice.exception.NoDistrictsFoundException;
import com.example.rentservice.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictDto> getAllDistricts() {
        return districtRepository.findAll().stream().map(DistrictDto::toDto).toList();
    }

    public List<DistrictDto> getAllDistrictsByName(String name) throws NoDistrictsFoundException {
        List<DistrictEntity> districts = districtRepository.findAllByNameLike(name);

        if (districts.isEmpty())
            throw new NoDistrictsFoundException("No districts found");

        return districts.stream().map(DistrictDto::toDto).toList();
    }

    public DistrictDto createDistrict(String name) throws DistrictAlreadyExistsException {
        if (districtRepository.findByName(name).isPresent())
            throw new DistrictAlreadyExistsException("District already exists");

        return DistrictDto.toDto(districtRepository.save(DistrictEntity.builder().name(name).build()));
    }
}
