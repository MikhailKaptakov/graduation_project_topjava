package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "restaurant.service.maxRevoteTime=00:00:01")
public class UserControllerRevoteAfterTest extends AbstractRestControllerTest {

    private static final String REST_URL = UserController.REST_URL + "/";

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("actual_restaurants").clear();
    }

    @Test
    void revote() throws Exception {
        User user = UserTestData.getUser();
        Restaurant restaurant = RestaurantTestData.getActualRestaurant2();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)152);

        postResultActions(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT2_ID, "",
                status().isUnprocessableEntity(), user);
    }
}
