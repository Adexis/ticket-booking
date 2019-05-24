package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.Movie;
import com.awalczak.ticketbooking.entities.Screening;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MovieDto extends AbstractDto {

    private String title;

    private List<Screening> screenings;

    public Movie toEntity() {
        return Movie.builder()
                .title(this.getTitle())
                .screenings(this.getScreenings())
                .build();
    }
}
