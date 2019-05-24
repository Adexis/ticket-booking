package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Room;
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
public class RoomDto extends AbstractDto {

    private Long number;

    private Long cinema;

    private List<Long> screenings;

    private List<SeatDto> seats;

    public Room toEntity() {
        Room room = Room.builder()
                .number(this.getNumber())
                .seats(this.getSeats()
                        .stream()
                        .map(SeatDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
        return room;
    }
}
