package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.Test;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.to.UserTo;
import ru.graduation_project_topjava.web.controllers.rest.AdminController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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