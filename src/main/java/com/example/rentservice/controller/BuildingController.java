package com.example.rentservice.controller;

import com.example.rentservice.dto.building.CreateBuildingRequest;
import com.example.rentservice.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @PostMapping("/create")
    private ResponseEntity createBuilding(@RequestBody CreateBuildingRequest request) {
        try {
            return ResponseEntity.ok(buildingService.createBuilding(request));
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
