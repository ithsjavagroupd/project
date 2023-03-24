package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.repository.ChainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    ChainController controller;

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

    @Test
    void addChainShouldReturn201() throws Exception {

        mockmvc.perform(MockMvcRequestBuilders
                .post("/chains")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(chain1))
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
