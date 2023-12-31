package com.example.rentservice.controller;

import com.example.rentservice.dto.room.*;
import com.example.rentservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity getRooms() {
        try {
            return ResponseEntity.ok(roomService.getRooms());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity getAvailableRooms() {
        try {
            return ResponseEntity.ok(roomService.getAvailableRooms());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available/in/building")
    public ResponseEntity getAvailableRoomsInBuilding(long id) {
        try {
            return ResponseEntity.ok(roomService.getAvailableRoomsInBuilding(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/rentals/in/building")
    public ResponseEntity getRoomInBuildingsWithRentals() {
        try {
            return ResponseEntity.ok(roomService.getRoomInBuildingsWithRentals());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/agreement")
    public ResponseEntity getAgreementRoomsByAgreementId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(roomService.getAgreementRoomsByAgreementId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/agreementRooms")
    public ResponseEntity getAgreementRoomsByRoomId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(roomService.getAgreementRoomsByRoomId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createRoom(@RequestBody CreateRoomRequest request) {
        try {
            return ResponseEntity.ok(roomService.createRoom(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addToCart")
    @PreAuthorize("hasAuthority('INDIVIDUAL') or hasAuthority('ENTITY')")
    public ResponseEntity addRoomToCart(@RequestBody AddRoomToCartRequest request) {
        try {
            return ResponseEntity.ok(roomService.addRoomToCart(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateRoom(@RequestBody UpdateRoomRequest request) {
        try {
            return ResponseEntity.ok(roomService.updateRoom(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteRoom(@RequestParam long id) {
        try {
            return ResponseEntity.ok(roomService.deleteRoom(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getRoomById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(roomService.getRoomById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/type/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createRoomType(@RequestBody CreateRoomTypeRequest request) {
        try {
            return ResponseEntity.ok(roomService.createRoomType(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/type/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateRoomType(@RequestBody UpdateRoomTypeRequest request) {
        try {
            return ResponseEntity.ok(roomService.updateRoomType(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/type/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateRoomType(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(roomService.deleteRoomType(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/type/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity addRoomTypes(@RequestBody AddRoomTypesToRoomRequest request) {
        try {
            return ResponseEntity.ok(roomService.addRoomTypesToRoom(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/types")
    public ResponseEntity getAllTypes() {
        try {
            return ResponseEntity.ok(roomService.getAllTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
