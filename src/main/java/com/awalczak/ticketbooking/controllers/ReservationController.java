package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.*;
import com.awalczak.ticketbooking.entities.Reservation;
import com.awalczak.ticketbooking.enums.Type;
import com.awalczak.ticketbooking.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_RESERVATION;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "reservation", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.createEntity(reservationDto);
    }

    @GetMapping(value = "reservation/all", produces = "application/json")
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllEntities();
    }

    @GetMapping(value = "reservation/one/{id}", produces = "application/json")
    public ReservationDto getReservationById(@PathVariable Long id) {
        return (ReservationDto) reservationService.getEntityById(id);
    }

    @DeleteMapping(value = "reservation/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservationById(@PathVariable Long id) {
        reservationService.deleteEntityById(id);
    }

    @GetMapping(value = "reservation/sample", produces = "application/json")
    private ReservationDto createSampleReservation() {
        return VALID_RESERVATION.toDto();
    }
}
