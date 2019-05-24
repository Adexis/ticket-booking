package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Screening;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScreeningDto extends AbstractDto {

    private Long cinema;

    private MovieDto movie;

    private RoomDto room;

    private LocalDateTime date;

    public Screening toEntity() {
        Screening screening = Screening.builder()
                .movie(this.getMovie().toEntity())
                .date(this.getDate())
                .build();
        if (this.room != null) {
            screening.setRoom(this.room.toEntity());
        }
        return screening;
    }
}
