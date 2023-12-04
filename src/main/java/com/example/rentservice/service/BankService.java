package com.example.rentservice.service;

import com.example.rentservice.dto.bank.BankDto;
import com.example.rentservice.dto.bank.CreateBankRequest;
import com.example.rentservice.dto.bank.UpdateBankRequest;
import com.example.rentservice.entity.BankEntity;
import com.example.rentservice.exception.BankAlreadyExistsException;
import com.example.rentservice.exception.BankNotFoundException;
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

    public List<BankDto> getAllBanks() throws BanksNotFoundException {
        List<BankEntity> banks = bankRepository.findAll();
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

    public BankDto updateBank(UpdateBankRequest request) throws BankNotFoundException {
        BankEntity bank = bankRepository.findById(request.getId()).orElseThrow(() -> new BankNotFoundException("Bank not found"));
        bank.setName(request.getName());
        return BankDto.toDto(bankRepository.save(bank));
    }

    public String deleteBank(Long id) throws BankNotFoundException {
        BankEntity bank = bankRepository.findById(id).orElseThrow(() -> new BankNotFoundException("Bank not found"));
        bankRepository.delete(bank);
        return "Bank was successfully deleted";
    }
}
