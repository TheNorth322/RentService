package com.example.rentservice.controller;

import com.example.rentservice.dto.agreement.CreateAgreementRequest;
import com.example.rentservice.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agreement")
public class AgreementController {
    @Autowired
    private AgreementService agreementService;

    @PostMapping("/create")
    public ResponseEntity createAgreement(@RequestBody CreateAgreementRequest request) {
        try {
            return ResponseEntity.ok(agreementService.createAgreement(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
