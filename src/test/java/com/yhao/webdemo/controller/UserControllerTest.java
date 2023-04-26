package com.yhao.webdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveUser() throws Exception {
        // {"name":"aaa", "password":"222"}
        String jsonStr = "{\"name\":\"aaa\", \"password\":\"222\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/userTest/saveUser")
                        .content(jsonStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(print());
    }

    @Test
    void getUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/userTest/getUser")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().is(200)).andDo(print());
    }
}