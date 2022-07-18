package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Test
    void getRestaurant() throws Exception{
        getResultActions(REST_URL + RestaurantTestData.getActualRestaurant().getId(),
                status().isOk(), UserTestData.getUser())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.getActualRestaurant()));
    }
}