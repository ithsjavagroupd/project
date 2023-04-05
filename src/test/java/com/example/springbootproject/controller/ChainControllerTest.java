package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.repository.ChainRepository;
import com.example.springbootproject.repository.MemberRepository;
import com.example.springbootproject.repository.StoreRepository;
import com.example.springbootproject.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ChainController.class)
@ContextConfiguration(classes = {SecurityConfig.class, ChainController.class})
class ChainControllerTest {
    Chain chain1;
    Chain chain2;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mockmvc;

    @MockBean
    ChainRepository repository;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    StoreRepository storeRepository;


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

        mockmvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getChainsShouldReturn200OK() throws Exception {
        when(repository.findAll()).thenReturn(List.of(chain1, chain2));
        mockmvc.perform(get("/api/chains")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getChainByIdShouldReturn200OkAndJsonObjectOfChainAndThenWeWillBeHappyAboutIt() throws Exception {
        when(repository.findChainById(1L)).thenReturn(chain1);
        mockmvc.perform(get("/api/chains/1/members"))
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(chain1, Chain.class))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addChainShouldReturn201() throws Exception {

        mockmvc.perform(post("/api/chains")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(chain1))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/chains/1"))
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
    @WithMockUser(roles = "ADMIN")
    void addChainWithInvalidBodyShouldReturn400BadRequest() throws Exception {
        mockmvc.perform(post("/api/chains")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new String[]{"test"}))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteChainShouldResultInTheNonExistensOfDeletedChainAlsoReturn200Ok() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(chain1));
        willDoNothing().given(repository).deleteById(1L);
        mockmvc.perform(delete("/api/chains/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateChainShouldReturn200Ok() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(chain1));
        Chain updateChain = new Chain();
        updateChain.setId(1L);
        updateChain.setName("First Kedjan New Name");
        updateChain.setAddress("Address 1.5");
        mockmvc.perform(put("/api/chains/{id}", updateChain.getId())
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(updateChain))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
