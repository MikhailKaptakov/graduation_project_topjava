package ru.graduation_project_topjava.web.controllers.rest;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.User;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static ru.graduation_project_topjava.TestUtil.userAuth;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
public class AbstractRestControllerTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

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
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    protected ResultActions postResultActions(String url,
                                              String json,
                                              ResultMatcher status,
                                              User authUser) throws Exception {
        return perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(authUser))
                .with(csrf().asHeader())
                .content(json))
                .andExpect(status)
                .andDo(print());
    }

    protected  ResultActions getResultActions(String url,
                                              ResultMatcher status,
                                              User authUser) throws Exception {
        return perform(MockMvcRequestBuilders.get(url)
                .with(userAuth(authUser))
                .with(csrf().asHeader()))
                .andExpect(status)
                .andDo(print());
    }


}
