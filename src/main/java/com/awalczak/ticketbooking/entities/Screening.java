package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.ScreeningDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Screening extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public ScreeningDto toDto() {
        return ScreeningDto.builder()
                .date(this.getDate())
                .room(this.getRoom().toDto())
                .movie(this.getMovie().toDto())
                .build();
    }
}
