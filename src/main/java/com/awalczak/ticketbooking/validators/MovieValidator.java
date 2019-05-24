package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieValidator extends AbstractValidator {

    public boolean isEntityValid(BaseEntity entity) {
        Movie movie = (Movie) entity;
        boolean result = false;
        if (movie != null) {
            log.info("Validation of movie " + movie.toString() + " in progress");
            result = isTitleValid(movie.getTitle());
        } else {
            log.error("Received null movie object during validation");
        }
        return result;
    }

    private boolean isTitleValid(String title) {
        boolean result = false;
        if (title != null) {
            result = !title.isEmpty() && Character.isUpperCase(title.charAt(0));
        } else {
            log.error("Title cannot be null");
        }
        if (!result) {
            log.error("Title " + title + " is not Valid");
        }
        return result;
    }
}