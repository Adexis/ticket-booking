package com.awalczak.ticketbooking.dto;

import com.awalczak.ticketbooking.entities.User;
import com.awalczak.ticketbooking.enums.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto extends AbstractDto {

    private String name;

    private String surname;

    private Type type;

    public User toEntity() {
        return User.builder()
                .name(this.getName())
                .surname(this.getSurname())
                .type(this.getType())
                .build();
    }
}
