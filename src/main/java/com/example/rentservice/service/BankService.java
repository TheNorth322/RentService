package com.example.rentservice.service;

import com.example.rentservice.dto.bank.BankDto;
import com.example.rentservice.dto.bank.CreateBankRequest;
import com.example.rentservice.entity.BankEntity;
import com.example.rentservice.exception.BankAlreadyExistsException;
import com.example.rentservice.exception.BanksNotFoundException;
import com.example.rentservice.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public List<BankDto> getAllBanks(String name) throws BanksNotFoundException {
        List<BankEntity> banks = bankRepository.findAllByNameLike(name);

        if (banks.isEmpty())
            throw new BanksNotFoundException("Banks not found");

        return banks.stream().map(BankDto::toDto).toList();
    }

    public String createBank(CreateBankRequest request) throws BankAlreadyExistsException {
        Optional<BankEntity> bankOptional = bankRepository.findByName(request.getName());

        if (bankOptional.isPresent())
            throw new BankAlreadyExistsException("Bank already exists");

        bankRepository.save(BankEntity
                .builder()
                .name(request.getName())
                .build());

        return "Bank was successfully created";
    }
}
