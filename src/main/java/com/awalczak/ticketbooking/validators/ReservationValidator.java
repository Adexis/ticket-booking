package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.awalczak.ticketbooking.validators.Constants.MINIMUM_MINUTES_BEFORE_SCREENING;

@Component
@Slf4j
public class ReservationValidator extends AbstractValidator {

    private final ScreeningValidator screeningValidator;
    private final SeatValidator seatValidator;
    private final UserValidator userValidator;

    public ReservationValidator(ScreeningValidator screeningValidator, SeatValidator seatValidator, UserValidator userValidator) {
        this.screeningValidator = screeningValidator;
        this.seatValidator = seatValidator;
        this.userValidator = userValidator;
    }

    public boolean isEntityValid(BaseEntity entity) {
        Reservation reservation = (Reservation) entity;
        boolean result = false;
        if (reservation != null) {
            log.info("Validation of reservation " + reservation.toString() + " in progress");
            result = this.containsRequiredFields(reservation.getScreening(), reservation.getSeats(), reservation.getUsers())
                    && isFinalPriceValid(reservation.getPrice())
                    && screeningValidator.isEntityValid(reservation.getScreening())
                    && isDateValid(reservation.getScreening().getDate())
                    && seatValidator.isListOfEntitiesValid(reservation.getSeats())
                    && !seatValidator.isAnyOfSeatsTaken(reservation.getSeats(), reservation.getScreening().getRoom().getSeats())
                    && !seatValidator.isSingleSeatLeftBetweenReservations(reservation.getSeats(), reservation.getScreening().getRoom().getSeats())
                    && userValidator.isListOfEntitiesValid(reservation.getUsers());
        } else {
            log.error("Received null reservation object during validation");
        }
        return result;
    }

    private boolean isFinalPriceValid(Double price) {
        boolean result = false;
        if (price != null) {
            result = price > 0.0;
        } else {
            log.error("Price cannot be null");
        }
        if (!result) {
            log.error("Final price must be greater than zero");
        }
        return result;
    }

    private boolean isDateValid(LocalDateTime date) {
        boolean result = false;
        if (date != null) {
            result = date.isAfter(LocalDateTime.now().plusMinutes(MINIMUM_MINUTES_BEFORE_SCREENING));
        } else {
            log.error("Date cannot be null");
        }
        if (!result) {
            log.error("Minimum time before screenings for creating reservation is equal to " + MINIMUM_MINUTES_BEFORE_SCREENING + " minutes");
        }
        return result;
    }
}