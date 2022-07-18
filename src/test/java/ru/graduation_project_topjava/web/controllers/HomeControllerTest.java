package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HomeControllerTest extends AbstractControllerTest {

    @Test
    void home() throws Exception{
        perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/home"));
    }
}