package com.awalczak.ticketbooking.controllers;

import com.awalczak.ticketbooking.dto.MovieDto;
import com.awalczak.ticketbooking.dto.RoomDto;
import com.awalczak.ticketbooking.dto.ScreeningDto;
import com.awalczak.ticketbooking.dto.SeatDto;
import com.awalczak.ticketbooking.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.awalczak.ticketbooking.controllers.TestConstants.VALID_SCREENING;

@RestController
public class ScreeningController {

    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping(value = "screening", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ScreeningDto createScreening(@RequestBody ScreeningDto screeningDto) {
        return screeningService.createEntity(screeningDto);
    }

        @GetMapping(value = "screening/interval/{from}/{to}", produces = "application/json")
    public List<ScreeningDto> getAllScreeningInTimeInterval(@PathVariable String from, @PathVariable String to) {
        return screeningService.getAllScreeningInTimeInterval(from, to);
    }

    @GetMapping(value = "screening/all", produces = "application/json")
    public List<ScreeningDto> getAllScreenings() {
        return screeningService.getAllEntities();
    }

    @GetMapping(value = "screening/one/{id}", produces = "application/json")
    public ScreeningDto getScreeningById(@PathVariable Long id) {
        return (ScreeningDto) screeningService.getEntityById(id);
    }

    @DeleteMapping(value = "screening/one/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteScreeningsById(@PathVariable Long id) {
        screeningService.deleteEntityById(id);
    }

    @GetMapping(value = "screening/sample", produces = "application/json")
    private ScreeningDto createSampleScreening() {
        return VALID_SCREENING.toDto();
    }
}
