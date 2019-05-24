package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.SeatDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seatsReserved")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Seat extends BaseEntity {

    @Column(length = 1, nullable = false)
    private String line;

    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private boolean taken;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    public SeatDto toDto() {
        return SeatDto.builder()
                .line(this.getLine())
                .number(this.getNumber())
                .taken(this.isTaken())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return line.equals(seat.line) &&
                number.equals(seat.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, number);
    }
}
