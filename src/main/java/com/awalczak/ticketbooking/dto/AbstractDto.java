package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.BaseEntity;

public abstract class AbstractDto {

    public abstract <E extends BaseEntity> E toEntity();
}
