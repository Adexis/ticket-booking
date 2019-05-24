package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.CinemaDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Cinema extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Room> rooms;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Screening> screenings;

    public CinemaDto toDto() {
        return CinemaDto.builder()
                .name(this.getName())
                .rooms(this.getRooms()
                        .stream()
                        .map(Room::toDto)
                        .collect(Collectors.toList()))
                .screenings(this.getScreenings()
                        .stream()
                        .map(Screening::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
