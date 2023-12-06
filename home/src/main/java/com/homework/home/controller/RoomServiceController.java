package com.homework.home.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.homework.home.dto.Home;
import com.homework.home.dto.Views;
import com.homework.home.dto.request.HomeRequest;
import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;
import com.homework.home.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomServiceController {
    private final RoomService roomService;

    public RoomServiceController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomEntity> createRoom(@RequestParam Long id, @RequestBody @Valid RoomRequest request) {
        RoomEntity roomEntity = roomService.createRoom(id, request);
        if (roomEntity != null) {
            return ResponseEntity.ok(roomEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        if (roomService.deleteRoom(roomId)) {
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<RoomEntity> changeRoom(@PathVariable Long roomId, @RequestBody @Valid RoomRequest request) {
        if (roomService.changeRoom(roomId, request)) {
            return ResponseEntity.ok(roomService.getRoom(roomId));
        }
        return ResponseEntity.notFound().build();
    }

}

