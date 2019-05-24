package com.awalczak.ticketbooking.entities;

import com.awalczak.ticketbooking.dto.AbstractDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;

    public abstract <D extends AbstractDto> D toDto();

}
