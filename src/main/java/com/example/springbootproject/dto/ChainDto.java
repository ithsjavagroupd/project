package com.example.springbootproject.dto;

import com.example.springbootproject.entity.Chain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChainDto {
    private Long id;
    private String name;

    public ChainDto(){}

    public ChainDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChainDto(Chain chain) {
        this.id = chain.getId();
        this.name = chain.getName();
    }

}
