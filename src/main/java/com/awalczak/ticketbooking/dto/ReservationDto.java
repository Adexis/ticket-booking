package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Reservation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReservationDto extends AbstractDto {

    private Double price;

    private ScreeningDto screening;

    private List<SeatDto> seats;

    private List<UserDto> users;

    public Reservation toEntity() {
        return Reservation.builder()
                .screening(this.getScreening().toEntity())
                .seats(this.getSeats()
                        .stream()
                        .map(SeatDto::toEntity)
                        .collect(Collectors.toList()))
                .users(this.getUsers()
                        .stream()
                        .map(UserDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}

