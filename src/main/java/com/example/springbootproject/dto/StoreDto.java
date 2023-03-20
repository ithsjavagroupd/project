package com.example.springbootproject.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {
    private Long id;

    private String name;


    public StoreDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
