package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.repository.ChainRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChainController.class)
class ChainControllerTest {
    Chain chain1;
    Chain chain2;

    @Autowired
    MockMvc mockmvc;

    @MockBean
    ChainRepository repository;

    @BeforeEach
    public void init() {
        chain1 = new Chain();
        chain2 = new Chain();
        chain1.setId(1L);
        chain1.setName("First Kedjan");
        chain1.setAddress("Address 1");
        chain2.setId(2L);
        chain2.setName("Second kedjan");
        chain2.setAddress("Address 2");
    }


    @Test
    void getChainsShouldReturn200OK() throws Exception {
        when(repository.findAll()).thenReturn(List.of(chain1, chain2));
        mockmvc.perform(get("/chains")).andExpect(status().isOk());
    }

    @Test
    void getChainByIdShouldReturn200OkAndJsonObjectOfChainAndThenWeWillBeHappyAboutIt() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(chain1));
        var result = mockmvc.perform(get("/chains/1"))
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(chain1, Chain.class))
                .andExpect(status().isOk());
    }

//    @Test
//    void getChainsShouldReturnListOfAllChainsAsJsonAnd200Ok() throws Exception {
//        List<Chain> allChains = new ArrayList<>();
//        allChains.add(chain1);
//        allChains.add(chain2);
//        when(repository.findAll()).thenReturn(List.of(chain1, chain2));
//        var result = mockmvc.perform(get("/chains"))
//                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(List.of(chain1, chain2), Chain.class))
//                .andExpect(status().isOk());
//    }



}
