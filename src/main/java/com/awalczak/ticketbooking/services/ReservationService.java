package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.dto.AbstractDto;
import com.awalczak.ticketbooking.dto.ReservationDto;
import com.awalczak.ticketbooking.entities.Reservation;
import com.awalczak.ticketbooking.entities.Screening;
import com.awalczak.ticketbooking.entities.Seat;
import com.awalczak.ticketbooking.repositories.ReservationRepository;
import com.awalczak.ticketbooking.validators.ReservationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservationService extends AbstractService {

    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final ScreeningService screeningService;
    private final RoomService roomService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationValidator reservationValidator, ReservationRepository reservationRepository1, ReservationValidator reservationValidator1, ScreeningService screeningService, RoomService roomService) {
        super(reservationRepository, reservationValidator);
        this.reservationRepository = reservationRepository1;
        this.reservationValidator = reservationValidator1;
        this.screeningService = screeningService;
        this.roomService = roomService;
    }

    public ReservationDto createEntity(AbstractDto dto) {
        ReservationDto reservationDto = (ReservationDto) dto;
        if (reservationDto != null) {
            Reservation reservation = reservationDto.toEntity();
            reservation.setPrice(calculatePrice(reservationDto));
            if (reservationValidator.isEntityValid(reservation)
                    && screeningService.screeningExists(reservation.getScreening())
                    && roomService.roomExist(reservation.getScreening().getRoom())) {
                Screening screening = screeningService.previousScreening(reservation.getScreening());
                reservation.setScreening(screening);
                setSeatsAsTaken(reservation);
                reservationRepository.saveAndFlush(reservation);
                log.info(LOG_INFO_CREATE_SUCCESS + reservation.toString());
                return reservation.toDto();
            } else {
                log.error(LOG_INFO_CREATE_FAILURE_VALIDATION + dto.toString());
            }
        } else {
            log.error(LOG_INFO_CREATE_FAILURE_DTO_IS_NULL);
        }
        return null;
    }

    private Double calculatePrice(ReservationDto reservationDto) {
        final Double[] finalPrice = {0D};
        reservationDto.getUsers().forEach(
                user -> {
                    switch (user.getType()) {
                        case ADULT:
                            finalPrice[0] += 25;
                            break;
                        case STUDENT:
                            finalPrice[0] += 18;
                            break;
                        case CHILD:
                            finalPrice[0] += 12.50;
                    }
                }
        );
        return finalPrice[0];
    }

    private void setSeatsAsTaken(Reservation reservation) {
        reservation.getScreening().getRoom().getSeats().forEach(seat ->
                reservation.getSeats().forEach(seatToBeReserved -> {
                    if (seat.equals(seatToBeReserved))  {
                        seat.setTaken(true);
                    }
                }));
    }
}
