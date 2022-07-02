package ru.graduation_project_topjava.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.service.RestaurantService;
import ru.graduation_project_topjava.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class AdminControllerTest {

    private static final String REST_URL = AdminController.REST_URL + "/";

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataJpaRestaurantRepository dataJpaRestaurantRepository;

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
    void getNotActual() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER
                        .contentJson(List.of(RestaurantTestData.getNotActualRestaurant())));
    }

    @Test
    void createRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setMeals(newMeals);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                /*.with(userHttpBasic(admin))*/
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);

        long newId = created.id();
        newRestaurant.setId(newId);
        newRestaurant.setLastUpdateDate(LocalDate.now());
        MealTestData.setMealsId(newMeals, AbstractBaseEntity.START_SEQ + 1);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER
                .assertMatch(dataJpaRestaurantRepository.findById(newId), newRestaurant);
    }

    @Test
    void addMealsMenu() throws Exception{
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant notActualRestaurant = RestaurantTestData.getNotActualRestaurant();
        notActualRestaurant.setMeals(newMeals);
        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                /*.with(userHttpBasic(admin))*/
                .content(JsonUtil.writeValue(newMeals)))
                .andExpect(status().isCreated());

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);

        notActualRestaurant.setLastUpdateDate(LocalDate.now());
        MealTestData.setMealsId(newMeals, AbstractBaseEntity.START_SEQ);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, notActualRestaurant);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER
                    .assertMatch(dataJpaRestaurantRepository.findById(RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID),
                            notActualRestaurant);
    }

    //todo add test to no valid data
}