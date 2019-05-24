package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Room;
import com.awalczak.ticketbooking.entities.Seat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SeatDto extends AbstractDto {

    private String line;

    private Long number;

    private boolean taken;

    private Room room;

    public Seat toEntity() {
        Seat seat = Seat.builder()
                .line(this.getLine())
                .number(this.getNumber())
                .taken(this.isTaken())
                .room(this.getRoom())
                .build();
        return seat;
    }
}
