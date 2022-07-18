package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.graduation_project_topjava.TimingExtension;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith(TimingExtension.class)
public class AbstractControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @PostConstruct
    protected void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
}
