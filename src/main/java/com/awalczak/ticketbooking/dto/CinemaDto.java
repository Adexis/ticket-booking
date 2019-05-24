package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Cinema;
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
public class CinemaDto extends AbstractDto {

    private String name;

    private List<RoomDto> rooms;

    private List<ScreeningDto> screenings;

    public Cinema toEntity() {
        return Cinema.builder()
                .name(this.getName())
                .rooms(this.getRooms().stream().map(RoomDto::toEntity).collect(Collectors.toList()))
                .screenings(this.getScreenings().stream().map(ScreeningDto::toEntity).collect(Collectors.toList()))
                .build();
    }
}
