package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.*;
import ru.graduation_project_topjava.repository.CrudVoteRepository;

import java.time.LocalDate;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "restaurant.service.maxRevoteTime=23:59:59")
class UserControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = UserController.REST_URL + "/";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CrudVoteRepository voteRepository;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("actual_restaurants").clear();
    }

    @Test
    void getActual() throws Exception {
        List<Restaurant> expected = RestaurantTestData.getActualWithMealsAndVotes();
        getResultActions(REST_URL, status().isOk(), UserTestData.getUser())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER
                        .contentJson(expected));
    }

    @Test
    void vote() throws Exception {
        User user = UserTestData.getUser2();
        Restaurant restaurant = RestaurantTestData.getActualRestaurant();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)AbstractBaseEntity.START_SEQ);

        ResultActions action = postResultActions(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT_ID, "",
                status().isCreated(), user);

        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
        VoteTestData.VOTE_MATCHER.assertMatch(created, expectedVote);

        Vote actualVote = voteRepository.getActualUserVote(user.getId(), LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
    }

    @Test
    void illegalVoteToNotActualRestaurant() throws Exception {
        User user = UserTestData.getUser2();
        Restaurant restaurant = RestaurantTestData.getNotActualRestaurant();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)AbstractBaseEntity.START_SEQ);

        postResultActions(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID, "",
                status().isUnprocessableEntity(), user);
    }

    @Test
    void revote() throws Exception {
        User user = UserTestData.getUser();
        Restaurant restaurant = RestaurantTestData.getActualRestaurant2();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)152);

        ResultActions action = postResultActions(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT2_ID, "",
                status().isCreated(), user);

        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
        VoteTestData.VOTE_MATCHER.assertMatch(created, expectedVote);
        Vote actualVote = voteRepository.getActualUserVote(user.getId(), LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
    }

    @Test
    void revoteToItself() throws Exception {
        User user = UserTestData.getUser();
        Restaurant restaurant = RestaurantTestData.getActualRestaurant();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)152);

        postResultActions(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT_ID, "",
                status().isUnprocessableEntity(), user);
    }
}