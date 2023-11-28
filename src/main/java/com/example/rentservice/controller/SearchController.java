package com.example.rentservice.controller;

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

    @GetMapping("/buildings")
    public ResponseEntity searchBuildingsByAddress(@RequestParam String address) {
        try {
            return ResponseEntity.ok(searchService.searchBuildingsByAddress(address));
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

    /*@GetMapping("/addresses")
    public ResponseEntity searchAdresses(@RequestBody SearchAddressesRequest request) {
        try {
            return ResponseEntity.ok(searchService.searchAddresses(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}
