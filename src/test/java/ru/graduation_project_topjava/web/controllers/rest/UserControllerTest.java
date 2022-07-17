package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.*;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static ru.graduation_project_topjava.TestUtil.userAuth;


@SpringBootTest(properties = "restaurant.service.maxRevoteTime=23:59:59")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class UserControllerTest {

    private static final String REST_URL = UserController.REST_URL + "/";

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CrudVoteRepository voteRepository;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("actual_restaurants").clear();
    }

    private ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getActual() throws Exception {
        List<Restaurant> expected = RestaurantTestData.getActualWithMealsAndVotes();
        List<Meal> expectedMeal = MealTestData.getAllActualMeals();
        List<Vote> expectedVotes = VoteTestData.getActualRestaurantVotes();
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(UserTestData.getUser()))
                .with(csrf().asHeader()))
                .andExpect(status().isOk())
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

        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(user))
                .with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isCreated());

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

        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(user))
                .with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void revote() throws Exception {
        User user = UserTestData.getUser();
        Restaurant restaurant = RestaurantTestData.getActualRestaurant2();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)152);
        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(user))
                .with(csrf().asHeader())
                .content(JsonUtil.writeValue(expectedVote)))
                .andExpect(status().isCreated());

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
        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.ACTUAL_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(user))
                .with(csrf().asHeader())
                .content(JsonUtil.writeValue(expectedVote)))
                .andExpect(status().isUnprocessableEntity());
    }

}