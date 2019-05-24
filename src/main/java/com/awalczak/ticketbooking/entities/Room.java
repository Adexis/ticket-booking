package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.RoomDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Room extends BaseEntity {

    @Column(nullable = false)
    private Long number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Screening> screenings;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Seat> seats;

    public RoomDto toDto() {
        return RoomDto.builder()
                .number(this.getNumber())
                .seats(this.getSeats()
                        .stream()
                        .map(Seat::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
