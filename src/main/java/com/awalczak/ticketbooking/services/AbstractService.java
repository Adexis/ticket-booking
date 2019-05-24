package com.awalczak.ticketbooking.services;

import com.awalczak.ticketbooking.dto.AbstractDto;
import com.awalczak.ticketbooking.entities.BaseEntity;
import com.awalczak.ticketbooking.validators.AbstractValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractService<R extends JpaRepository, V extends AbstractValidator, D extends AbstractDto, E extends BaseEntity> {

    static final String LOG_INFO_CREATE_SUCCESS = "Created entity with params: ";
    static final String LOG_INFO_CREATE_FAILURE_DTO_IS_NULL = "Entity cannot be created: Cinema DTO object is null";
    static final String LOG_INFO_CREATE_FAILURE_VALIDATION = "Entity with following params not created due to validation: ";
    private static final String LOG_INFO_GET_FAILURE_ID_IS_INCORRECT = "Entity with following id cannot be found: ";
    private static final String LOG_INFO_GENERAL_FAILURE_ID_IS_NULL = "Entity cannot be found: id is null";
    private static final String LOG_INFO_DELETE_SUCCESS = "Deleted entity with ID: ";

    private final R repository;
    private final V validator;

    AbstractService(R repository, V validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public D createEntity(D dto) {
        if (dto != null) {
            E entity = dto.toEntity();
            if (validator.isEntityValid(entity)) {
                repository.save(entity);
                log.info(LOG_INFO_CREATE_SUCCESS + entity.toString());
                return dto;
            } else {
                log.info(LOG_INFO_CREATE_FAILURE_VALIDATION + dto.toString());
            }
        } else {
            log.info(LOG_INFO_CREATE_FAILURE_DTO_IS_NULL);
        }
        return null;
    }

    public List<D> getAllEntities() {
        List<E> allEntities = repository.findAll();
        List<D> allDtos = new ArrayList<>();
        for (E entity : allEntities) {
            allDtos.add(entity.toDto());
        }
        return allDtos;
    }

    public D getEntityById(Long id) {
        if (id != null) {
            Optional<E> entityOptional = repository.findById(id);
            if (entityOptional.isPresent()) {
                return entityOptional.get().toDto();
            } else {
                log.info(LOG_INFO_GET_FAILURE_ID_IS_INCORRECT + id);
            }
        } else {
            log.info(LOG_INFO_GENERAL_FAILURE_ID_IS_NULL);
        }
        return null;
    }

    public void deleteEntityById(Long id) {
        if (id != null) {
            repository.deleteById(id);
            log.info(LOG_INFO_DELETE_SUCCESS + id);
        } else {
            log.info(LOG_INFO_GENERAL_FAILURE_ID_IS_NULL);
        }
    }
}
