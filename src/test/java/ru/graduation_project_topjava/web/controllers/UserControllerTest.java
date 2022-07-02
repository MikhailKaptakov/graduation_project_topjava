package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.*;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.service.UserService;
import ru.graduation_project_topjava.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Transactional
@SpringBootTest
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
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CrudVoteRepository voteRepository;

    private ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                /*.apply(springSecurity())*/
                .build();
    }

    @Test
    void getActual() throws Exception {
        Restaurant expected = RestaurantTestData.getActualRestaurant();
        List<Meal> expectedMeal = MealTestData.getAllActualMeals();
        List<Vote> expectedVotes = VoteTestData.getActualRestaurant2Votes();
        expected.setMeals(expectedMeal);
        expected.setVotes(expectedVotes);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER
                        .contentJson(List.of(expected)));
    }

    @Test
    void vote() throws Exception {
        User user = UserTestData.getUser2();
        Restaurant restaurant = RestaurantTestData.getNotActualRestaurant();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)AbstractBaseEntity.START_SEQ);

        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL+RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                /*.with(userHttpBasic(admin))*/
                .content(JsonUtil.writeValue(expectedVote)))
                .andExpect(status().isCreated());

        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
        VoteTestData.VOTE_MATCHER.assertMatch(created, expectedVote);

        Vote actualVote = voteRepository.getVote(user.getId(), LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
    }

    @Test
    void revote() throws Exception {
        User user = UserTestData.getUser();
        Restaurant restaurant = RestaurantTestData.getNotActualRestaurant();
        Vote expectedVote = new Vote(user, restaurant);
        expectedVote.setId((long)152);

        if (LocalTime.now().isAfter(UserService.maxRevoteTime)) {
            ResultActions action = perform(MockMvcRequestBuilders
                    .post(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID)
                    /*.with(userHttpBasic(admin))*/
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(expectedVote)))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        } else {
            ResultActions action = perform(MockMvcRequestBuilders
                    .post(REST_URL+RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    /*.with(userHttpBasic(admin))*/
                    .content(JsonUtil.writeValue(expectedVote)))
                    .andExpect(status().isCreated());

            Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
            VoteTestData.VOTE_MATCHER.assertMatch(created, expectedVote);

            Vote actualVote = voteRepository.getVote(user.getId(), LocalDate.now()).orElse(null);
            VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
        }
    }
}