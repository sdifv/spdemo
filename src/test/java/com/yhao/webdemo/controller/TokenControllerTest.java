package com.yhao.webdemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@Slf4j
@SpringBootTest
public class TokenControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void idempotenceTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/tokenTest/getToken")
                                             .param("uid", "111")
                                             .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(result, new TypeReference<Map>() {});
        String token = map.get("data").toString();
        log.info("获取的token字符串：" + token);

        for (int i = 0; i < 3; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders.post("/tokenTest/oncePost")
                                                             .param("uid", "111")
                                                             .header("token", token)
                                                             .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn().getResponse().getContentAsString();
            map = mapper.readValue(result, new TypeReference<Map>() {});
            System.out.println(map.get("data").toString());
        }
    }
}
