package com.example.springbootproject.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

    private long id;
    @NonNull
    private String name;

    public EmployeeDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
