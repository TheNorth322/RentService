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
