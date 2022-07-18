package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.VoteTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";

    @Test
    void getVote() throws Exception{
        getResultActions(REST_URL + VoteTestData.getActualVote1UserActualRestaurant().getId(),
                status().isOk(), UserTestData.getUser())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.getActualVote1UserActualRestaurant()));
    }
}