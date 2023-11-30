package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.AddressPartDto;
import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.entity.AddressPartEntity;
import com.example.rentservice.exception.AddressAlreadyExistsException;
import com.example.rentservice.exception.AddressNotFoundException;
import com.example.rentservice.repository.AddressPartRepository;
import com.example.rentservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressPartRepository addressPartRepository;

    public AddressEntity createAddress(CreateAddressRequest request) throws AddressAlreadyExistsException {
        if (addressRepository.findByName(request.getName()).isPresent())
            throw new AddressAlreadyExistsException("Address already exists");

        return addressRepository.save(AddressEntity
                .builder()
                .name(request.getName())
                .addressParts(request.getAddressParts().stream().map(this::getAddressPart).collect(Collectors.toSet()))
                .build());
    }

    public AddressEntity findByName(String name, List<AddressPartDto> addressPart) throws AddressNotFoundException {
        return addressRepository.findByName(name).orElse(addressRepository.save(AddressEntity
                .builder()
                        .name(name)
                        .addressParts(addressPart.stream().map(this::getAddressPart).collect(Collectors.toSet()))
                .build())
        );
    }
    private AddressPartEntity getAddressPart(AddressPartDto addressPartDto) {
        return addressPartRepository.findByObjectGuid(addressPartDto.getObjectGuid()).orElse(
                addressPartRepository.save(AddressPartEntity
                        .builder()
                        .fullTypeName(addressPartDto.getFullTypeName())
                        .level(addressPartDto.getLevel())
                        .typeName(addressPartDto.getTypeName())
                        .objectGuid(addressPartDto.getObjectGuid())
                        .name(addressPartDto.getName())
                        .build()
                )
        );
    }
}
