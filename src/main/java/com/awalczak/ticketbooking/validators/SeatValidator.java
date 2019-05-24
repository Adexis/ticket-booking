package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.entities.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SeatValidator extends AbstractValidator {

    public boolean isEntityValid(BaseEntity entity) {
        Seat seat = (Seat) entity;
        boolean result = false;
        if (seat != null) {
            log.info("Validation of seat " + seat.toString() + " in progress");
            result = this.containsRequiredFields(seat.getNumber(), seat.getLine())
                    && isSeatLineValid(seat.getLine())
                    && isSeatNumberValid(seat.getNumber());
        } else {
            log.error("Received null seat object during validation");
        }
        return result;
    }

    private boolean isSeatLineValid(String row) {
        boolean result = false;
        if (row != null) {
            result = row.length() == 1;
        } else {
            log.error("Row cannot be null");
        }
        if (!result) {
            log.error("Row is not valid");
        }
        return result;
    }

    private boolean isSeatNumberValid(Long number) {
        boolean result = false;
        if (number != null) {
            result = number > 0;
        } else {
            log.error("Seat number cannot be null");
        }
        if (!result) {
            log.error("Seat number must be greater than zero");
        }
        return result;
    }

    boolean isAnyOfSeatsTaken(List<Seat> seatsToBeReserved, List<Seat> seats) {
        for (Seat seat : seats) {
            for (Seat seatToBeReserved : seatsToBeReserved) {
                if (seat.equals(seatToBeReserved) && seat.isTaken()) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isSingleSeatLeftBetweenReservations(List<Seat> seatsToBeReserved, List<Seat> seats) {
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            for (int j = 0; j < seatsToBeReserved.size(); j++) {
                Seat seatReserved = seatsToBeReserved.get(j);
                if (seat.equals(seatReserved)) {
                    if (isSingleSeatLeftBetweenSeats(seatsToBeReserved, seats, seat, i - 1, i - 2)
                            || isSingleSeatLeftBetweenSeats(seatsToBeReserved, seats, seat, i + 1, i + 2)) {
                        log.info("There is a single seat between two reservations. Cannot create reservation");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isSingleSeatLeftBetweenSeats(List<Seat> seatsReserved, List<Seat> seats, Seat seat,
                                                 int firstSeatNumber, int secondSeatNumber) {
        return firstSeatNumber >= 0
                && secondSeatNumber >= 0
                && firstSeatNumber < seats.size()
                && secondSeatNumber < seats.size()
                && seats.get(firstSeatNumber) != null
                && seats.get(secondSeatNumber) != null
                && !seatsReserved.contains(seats.get(firstSeatNumber))
                && seats.get(firstSeatNumber).getLine().equals(seat.getLine())
                && seats.get(secondSeatNumber).getLine().equals(seat.getLine())
                && !seats.get(firstSeatNumber).isTaken()
                && seats.get(secondSeatNumber).isTaken();
    }
}
