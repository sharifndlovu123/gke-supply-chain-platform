package com.example.releases;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReleaseControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void recordsAndListsReleases() throws Exception {
        mvc.perform(post("/releases").contentType(MediaType.APPLICATION_JSON)
                .content("{\"service\":\"api\",\"version\":\"1.2.0\"}"))
            .andExpect(status().isOk());
        mvc.perform(get("/releases"))
            .andExpect(jsonPath("$[0].service").value("api"));
    }

    @Test
    void reportsVersion() throws Exception {
        mvc.perform(get("/version")).andExpect(content().string("local"));
    }
}
