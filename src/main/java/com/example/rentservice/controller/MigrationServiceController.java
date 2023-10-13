package com.example.rentservice.controller;

import com.example.rentservice.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration_service")
public class MigrationServiceController {
    @Autowired
    private MigrationService migrationService;

    @GetMapping("/all")
    private ResponseEntity getMigrationServices() {
        try {
            return ResponseEntity.ok(migrationService.getMigrationServices());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createMigrationService(@RequestParam String name) {
        try {
            return ResponseEntity.ok(migrationService.createMigrationService(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
