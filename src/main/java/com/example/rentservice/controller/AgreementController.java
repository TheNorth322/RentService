package com.example.rentservice.controller;

import com.example.rentservice.dto.agreement.CreateAgreementRequest;
import com.example.rentservice.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/generate/pdf")
    public ResponseEntity generateAgreementPdf(@RequestParam Long id) {
        try {
            byte[] pdfBytes = agreementService.generateAgreementPdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "agreement.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
