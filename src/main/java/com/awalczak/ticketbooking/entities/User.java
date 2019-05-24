package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.UserDto;
import com.awalczak.ticketbooking.enums.Type;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    public UserDto toDto() {
        return UserDto.builder()
                .name(this.getName())
                .surname(this.getSurname())
                .type(this.getType())
                .build();
    }
}
