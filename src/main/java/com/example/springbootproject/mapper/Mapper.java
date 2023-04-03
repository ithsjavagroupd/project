package com.example.springbootproject.mapper;

import com.example.springbootproject.dto.ChainDto;
import com.example.springbootproject.entity.Chain;


public class Mapper {

    public Chain map(ChainDto chain) {
        var c = new Chain();
        c.setId(chain.getId());
        c.setName(chain.getName());
        c.setAddress(chain.getAddress());
        return c;
    }


}
