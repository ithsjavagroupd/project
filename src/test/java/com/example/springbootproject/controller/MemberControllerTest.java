package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Member;
import com.example.springbootproject.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    Member member1;
    Member member2;
    TestRestTemplate restTemplate;

    @Autowired
    MockMvc mockmvc;

    @MockBean
    MemberRepository repository;


    @BeforeEach
    public void init() {
        member1 = new Member();
        member2 = new Member();
        member1.setId(1L);
        member1.setName("member");
        member1.setPhoneNumber("0707070707");
        member1.setEmail("member1@store.se");
        member2.setId(2L);
        member2.setName("This member");
        member2.setPhoneNumber("112");
        member2.setEmail("pippi@långstrump.se");
    }


    @Test
    void getMembersShouldReturn200OK() throws Exception {
        when(repository.findAll()).thenReturn(List.of(member1, member2));
        mockmvc.perform(get("/members")).andExpect(status().isOk());
    }

    @Test
    void getMemberByIdShouldReturn200OkAndJsonObjectOfMemberAndThenWeWillBeHappyAboutIt() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(member1));
        var result = mockmvc.perform(get("/members/1"))
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(member1, Member.class))
                .andExpect(status().isOk());
    }

    @Test
    void addMemberShouldReturn201() throws Exception {

        mockmvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(member1))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/members/1"))
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
    void addMemberWithInvalidBodyShouldReturn400BadRequest() throws Exception {
        mockmvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new String[]{"test"}))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteMemberShouldResultInTheNonExistensOfDeletedMemberAlsoReturn200Ok() throws Exception{
        when(repository.findById(member1.getId())).thenReturn(Optional.of(member1));
        willDoNothing().given(repository).deleteById(member1.getId());
        ResultActions response = mockmvc.perform(delete("/members/{id}", member1.getId()));
        response.andExpect(status().isOk());
        Mockito.verify(repository).deleteById(member1.getId());
    }

    @Test
    void updateMemberShouldReturn200Ok() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(member1));
        Member updateMember = new Member();
        updateMember.setId(1L);
        updateMember.setName("Kanin");
        updateMember.setEmail("annam@mail.se");
        updateMember.setPhoneNumber("031-112233");
        mockmvc.perform(put("/members/{id}" , updateMember.getId())
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(updateMember))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
