package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.RoomDto;
import com.awalczak.ticketbooking.dto.SeatDto;
import com.awalczak.ticketbooking.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_ROOM_1;

@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(value = "room", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public RoomDto createRoom(@RequestBody RoomDto roomDto) {
        return roomService.createEntity(roomDto);
    }

    @GetMapping(value = "room/all", produces = "application/json")
    public List<RoomDto> getAllRooms() {
        return roomService.getAllEntities();
    }

    @GetMapping(value = "room/one/{id}", produces = "application/json")
    public RoomDto getRoomById(@PathVariable Long id) {
        return (RoomDto) roomService.getEntityById(id);
    }

    @DeleteMapping(value = "room/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRoomById(@PathVariable Long id) {
        roomService.deleteEntityById(id);
    }

    @GetMapping(value = "room/sample")
    private RoomDto createSampleRoom() {
        return VALID_ROOM_1.toDto();
    }
}
