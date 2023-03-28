package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.entity.Member;
import com.example.springbootproject.repository.ChainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ChainController.class)
class ChainControllerTest {
    Chain chain1;
    Chain chain2;
    TestRestTemplate restTemplate;

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

    @Test
    void addChainShouldReturn201() throws Exception {

        mockmvc.perform(post("/chains")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(chain1))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/chains/1"))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void addChainWithInvalidBodyShouldReturn400BadRequest() throws Exception {
        mockmvc.perform(post("/chains")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new String[]{"test"}))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteChainShouldResultInTheNonExistensOfDeletedChainAlsoReturn200Ok() throws Exception{
        willDoNothing().given(repository).deleteById(chain1.getId());
        ResultActions response = mockmvc.perform(delete("/chains/{id}", chain1.getId()));
        response.andExpect(status().isOk());
    }

    @Test
    void updateChainShouldReturn200Ok() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(chain1));
        Chain updateChain = new Chain();
        updateChain.setId(1L);
        updateChain.setName("First Kedjan New Name");
        updateChain.setAddress("Address 1.5");
        mockmvc.perform(put("/chains/{id}" , updateChain.getId())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(updateChain))
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
