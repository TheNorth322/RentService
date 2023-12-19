package com.example.rentservice.controller;

import com.example.rentservice.dto.AddressDto;
import com.example.rentservice.dto.SearchAddressesRequest;
import com.example.rentservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/building/rooms/available")
    private ResponseEntity findAvailableRoomsInBuilding(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(searchService.findAvailableRoomsInBuilding(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/building/rooms/occupied")
    private ResponseEntity findRoomsInBuildingsWithRentals() {
        try {
            return ResponseEntity.ok(searchService.findRoomsInBuildingsWithRentals());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/individual/passport/in/migrationService")
    private ResponseEntity findAllByPassportMigrationService(Long id) {
        try {
            return ResponseEntity.ok(searchService.findAllByPassportMigrationService(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/building")
    private ResponseEntity getBuildingByAddress(@RequestBody AddressDto address) {
        try {
            return ResponseEntity.ok(searchService.getBuildingByAddress(address));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/migrationServices")
    public ResponseEntity searchMigrationServicesByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(searchService.searchMigrationServicesByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/rooms")
    public ResponseEntity searchRoomsByType(@RequestParam Long typeId) {
        try {
            return ResponseEntity.ok(searchService.searchRoomsByType(typeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity searchAddresses(@RequestParam String query, @RequestParam int count) {
        try {
            return ResponseEntity.ok(searchService.searchAddresses(query, count));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/migrationServices/all")
    public ResponseEntity searchMigrationServices() {
        try {
            return ResponseEntity.ok(searchService.searchMigrationServices());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
