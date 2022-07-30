package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationControllerTest extends AbstractControllerTest {

    private static final String URL = RegistrationController.URL;

    @Test
    void registerForm() throws Exception{
        perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

}