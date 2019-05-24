package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.ReservationDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Reservation extends BaseEntity {

    @Column(nullable = false)
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Seat> seats;

    public ReservationDto toDto() {
        return ReservationDto.builder()
                .price(this.getPrice())
                .screening(this.getScreening().toDto())
                .seats(this.getSeats()
                        .stream()
                        .map(Seat::toDto)
                        .collect(Collectors.toList()))
                .users(this.getUsers()
                        .stream()
                        .map(User::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
