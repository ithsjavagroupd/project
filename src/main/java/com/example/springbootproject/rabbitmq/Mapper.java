package com.example.springbootproject.rabbitmq;

import com.example.springbootproject.rabbitmq.ChainDto;
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
