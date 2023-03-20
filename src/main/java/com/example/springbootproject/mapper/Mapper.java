package com.example.springbootproject.mapper;

import com.example.springbootproject.dto.ChainDto;
import com.example.springbootproject.entity.Chain;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Mapper {

    //convertToEntity
    public List<ChainDto> map(List<Chain> chains) {
        return chains.stream().map((chain -> new ChainDto((chain.getId()), chain.getName()))).toList();
    }

    //convertToDto
    public Chain map(ChainDto chainDto) {
        var chain = new Chain();
        chain.setId(chainDto.getId());
        chain.setName(chainDto.getName());
        return chain;
    }

    //one vs list
    public ChainDto map(Chain chain) {
        return new ChainDto(chain);
    }

    public ChainDto map(Optional<Chain> byId) {
        return map(byId.get());
    }
}
