package com.example.springbootproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChainDto {
    private Long id;
    private String name;

    public ChainDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
