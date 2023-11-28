package com.example.rentservice.controller;

import com.example.rentservice.dto.bank.CreateBankRequest;
import com.example.rentservice.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping("/all")
    public ResponseEntity getAllBanks(@RequestParam String name) {
        try {
            return ResponseEntity.ok(bankService.getAllBanks(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createBank(@RequestBody CreateBankRequest request) {
        try {
            return ResponseEntity.ok(bankService.createBank(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
