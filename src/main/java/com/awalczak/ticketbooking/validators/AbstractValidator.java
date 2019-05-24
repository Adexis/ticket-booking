package com.awalczak.ticketbooking.validators;

import com.awalczak.ticketbooking.entities.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public abstract class AbstractValidator<E extends BaseEntity> {

    public abstract boolean isEntityValid(E entity);

    public boolean isListOfEntitiesValid(List<E> entities) {
        if (entities != null && entities.size() > 0) {
            for (E e : entities) {
                if (!this.isEntityValid(e)) return false;
            }
        } else {
            log.info("Entity list is either null or empty");
            return false;
        }
        return true;
    }

    public boolean containsRequiredFields(Object... objects) {
        final boolean result = Stream.of(objects).noneMatch(Objects::isNull);
        if (!result) {
            log.info("One of required fields is null");
        }
        return result;
    }
}
