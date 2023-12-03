package com.example.rentservice.controller;

import com.example.rentservice.dto.CreateMigrationServiceRequest;
import com.example.rentservice.dto.migrationService.UpdateMigrationServiceRequest;
import com.example.rentservice.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createMigrationService(@RequestBody CreateMigrationServiceRequest request) {
        try {
            return ResponseEntity.ok(migrationService.createMigrationService(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateMigrationService(@RequestBody UpdateMigrationServiceRequest request) {
        try {
            return ResponseEntity.ok(migrationService.updateMigrationService(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteMigrationService(@RequestParam long id) {
        try {
            return ResponseEntity.ok(migrationService.deleteMigrationService(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
