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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressPartRepository addressPartRepository;

    public AddressEntity findAddress(AddressDto addressDto) throws AddressNotFoundException {
        return addressRepository.findByName(addressDto.getName()).orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }

    public AddressEntity createAddress(CreateAddressRequest request) {
        return addressRepository.findByName(request.getName()).orElseGet( () -> {
            Set<AddressPartEntity> addressParts = request.getAddressParts().stream().map(this::getAddressPart).collect(Collectors.toSet());
            return addressRepository.save(AddressEntity
                    .builder()
                    .name(request.getName())
                    .addressParts(addressParts)
                    .build());
        });
    }

    private AddressPartEntity getAddressPart(AddressPartDto addressPartDto) {
        Optional<AddressPartEntity> addressPart = addressPartRepository.findByObjectGuid(addressPartDto.getObjectGuid());

        return addressPart.orElseGet(() -> addressPartRepository.save(AddressPartEntity
                .builder()
                .fullTypeName(addressPartDto.getFullTypeName())
                .level(addressPartDto.getLevel())
                .typeName(addressPartDto.getTypeName())
                .objectGuid(addressPartDto.getObjectGuid())
                .name(addressPartDto.getName())
                .build()
        ));
    }

}
