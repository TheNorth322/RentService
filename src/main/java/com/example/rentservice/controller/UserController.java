package com.example.rentservice.controller;

import com.example.rentservice.service.PassportService;
import com.example.rentservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PassportService passportService;

    @GetMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{username}/individual")
    public ResponseEntity getUserIndividualInfo(@PathVariable String username) {
        try {
            return ResponseEntity.ok(passportService.getUserIndividualInfo(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{username}/entity")
    public ResponseEntity getUserEntityInfo(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserEntityInfo(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/set/passport")
    public ResponseEntity setActivePassport(@RequestParam String token, @RequestParam(name = "passport_id") Long passportId) {
        try {
            return ResponseEntity.ok(passportService.setActivePassport(token, passportId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{username}/agreements")
    public ResponseEntity getUserAgreements(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserAgreements(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
