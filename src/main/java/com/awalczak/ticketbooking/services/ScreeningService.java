package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.dto.AbstractDto;
import com.awalczak.ticketbooking.dto.ScreeningDto;
import com.awalczak.ticketbooking.entities.Cinema;
import com.awalczak.ticketbooking.entities.Screening;
import com.awalczak.ticketbooking.repositories.CinemaRepository;
import com.awalczak.ticketbooking.repositories.ScreeningRepository;
import com.awalczak.ticketbooking.validators.ScreeningValidator;
import javafx.stage.Screen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class ScreeningService extends AbstractService {

    private static final String LOG_INFO_GET_FAILURE_DATE_IS_NULL = "Entity cannot be created: Date is null";
    private static final String DATE_FORMAT = "ddMMyyyyHHmm";
    private static final String LOG_INFO_SCREENING_DOES_NOT_EXIST = "Screening does not exist";
    private static final String LOG_INFO_SCREENING_IS_NULL = "Screening is null. Therefore screening does not exist";
    private static final String LOG_INFO_SCREENING_CINEMA_DOES_NOT_EXIST = "Cannot create room, the cinema does not exist.";
    private final ScreeningRepository screeningRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreeningValidator screeningValidator;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository, ScreeningValidator screeningValidator, ScreeningRepository screeningRepository1, CinemaRepository cinemaRepository, ScreeningValidator screeningValidator1) {
        super(screeningRepository, screeningValidator);
        this.screeningRepository = screeningRepository1;
        this.cinemaRepository = cinemaRepository;
        this.screeningValidator = screeningValidator1;
    }

    public ScreeningDto createEntity(AbstractDto abstractDto) {
        ScreeningDto screeningDto = (ScreeningDto) abstractDto;
        if (screeningDto != null) {
            Screening screening = screeningDto.toEntity();
            if (screeningDto.getCinema() != null && screeningValidator.isEntityValid(screening)) {
                addScreeningToExistingCinema(screeningDto);
                return screeningDto;
            } else {
                if (screeningValidator.isEntityValid(screening)) {
                    screeningRepository.save(screening);
                    log.info(LOG_INFO_CREATE_SUCCESS + screening.toString());
                    return screeningDto;
                } else {
                    log.error(LOG_INFO_CREATE_FAILURE_VALIDATION + screeningDto.toString());
                }
            }
        } else {
            log.error(LOG_INFO_CREATE_FAILURE_DTO_IS_NULL);
        }
        return null;
    }

    public List<ScreeningDto> getAllScreeningInTimeInterval(String from, String to) {
        if (from != null && to != null) {
            LocalDateTime fromDate = parseDateFromString(from);
            LocalDateTime toDate = parseDateFromString(to);
            return screeningRepository.findByDateBetweenOrderByMovieTitleAscDateAsc(fromDate, toDate)
                    .stream()
                    .map(Screening::toDto)
                    .collect(toList());
        } else {
            log.error(LOG_INFO_GET_FAILURE_DATE_IS_NULL);
        }
        return null;
    }

    public boolean screeningExists(Screening screening) {
        boolean result = false;
        if (screening != null) {
            List<Screening> screenings = screeningRepository.findByDateAndRoomNumber(screening.getDate(), screening.getRoom().getNumber());
            if (!screenings.isEmpty()) {
                result = true;
            } else {
                log.error(LOG_INFO_SCREENING_DOES_NOT_EXIST);
            }
        } else  {
            log.error(LOG_INFO_SCREENING_IS_NULL);
        }
        return result;
    }

    public Screening previousScreening(Screening screening) {
        return screeningRepository.findByDateAndRoomNumber(screening.getDate(), screening.getRoom().getNumber()).get(0);
    }

    private void addScreeningToExistingCinema(ScreeningDto screeningDto) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(screeningDto.getCinema());
        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            Screening screening = screeningDto.toEntity();
            List<Screening> screenings = cinema.getScreenings();
            screenings.add(screening);
            cinema.setScreenings(screenings);
            cinemaRepository.save(cinema);
            log.info(LOG_INFO_CREATE_SUCCESS + screening.toString());
        } else {
            log.error(LOG_INFO_SCREENING_CINEMA_DOES_NOT_EXIST);
        }
    }

    private LocalDateTime parseDateFromString(String stringDate) {
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
