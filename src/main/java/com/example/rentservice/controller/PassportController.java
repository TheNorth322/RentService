package com.example.rentservice.controller;

import com.example.rentservice.dto.passport.AddPassportRequest;
import com.example.rentservice.dto.passport.UpdatePassportRequest;
import com.example.rentservice.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor
public class PassportController {

    @Autowired
    private PassportService passportService;

    @GetMapping("/get")
    public ResponseEntity getPassportById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(passportService.getPassportById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity addPassport(@RequestBody AddPassportRequest request) {
        try {
            return ResponseEntity.ok(passportService.addPassport(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity updatePassport(@RequestBody UpdatePassportRequest request) {
        try {
            return ResponseEntity.ok(passportService.updatePassport(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity deletePassport(@RequestParam String username,
                                          @RequestParam(name = "passport_id") Long passportId) {
        try {
            return ResponseEntity.ok(passportService.deletePassport(username, passportId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
