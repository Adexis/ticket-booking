package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.MovieDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Movie extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Screening> screenings;

    public MovieDto toDto() {
        return MovieDto.builder()
                .title(this.getTitle())
                .build();
    }
}
