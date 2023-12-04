package com.example.rentservice.controller;

import com.example.rentservice.dto.building.CreateBuildingRequest;
import com.example.rentservice.dto.building.UpdateBuildingRequest;
import com.example.rentservice.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @GetMapping("/get")
    private ResponseEntity getBuilding(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(buildingService.getBuilding(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity createBuilding(@RequestBody CreateBuildingRequest request) {
        try {
            return ResponseEntity.ok(buildingService.createBuilding(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity updateBuilding(@RequestBody UpdateBuildingRequest request) {
        try {
            return ResponseEntity.ok(buildingService.updateBuilding(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    private ResponseEntity deleteBuilding(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(buildingService.deleteBuilding(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/rooms")
    private ResponseEntity getRooms(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(buildingService.getRooms(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
