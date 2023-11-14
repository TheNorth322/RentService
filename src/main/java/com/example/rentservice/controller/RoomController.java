package com.example.rentservice.controller;

import com.example.rentservice.dto.room.AddRoomTypesToRoomRequest;
import com.example.rentservice.dto.room.CreateRoomRequest;
import com.example.rentservice.dto.room.CreateRoomTypeRequest;
import com.example.rentservice.service.RoomService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createRoom(@RequestBody CreateRoomRequest request) {
        try {
            return ResponseEntity.ok(roomService.createRoom(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getRoomById(@RequestPart Long id) {
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
