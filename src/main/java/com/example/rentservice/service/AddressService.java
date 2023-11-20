package com.example.rentservice.service;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.CreateAddressRequest;
import com.example.rentservice.entity.AddressEntity;
import com.example.rentservice.exception.AddressAlreadyExistsException;
import com.example.rentservice.exception.AddressNotFoundException;
import com.example.rentservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressEntity createAddress(CreateAddressRequest request) throws AddressAlreadyExistsException {
        if (addressRepository.findByFiasId(request.getFiasId()).isPresent())
            throw new AddressAlreadyExistsException("Address already exists");

        return addressRepository.save(AddressEntity
                .builder()
                .name(request.getName())
                .fiasId(request.getFiasId())
                .build());
    }

    public AddressEntity findByFiasId(String fiasId) throws AddressNotFoundException {
        return addressRepository.findByFiasId(fiasId).orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }
}
