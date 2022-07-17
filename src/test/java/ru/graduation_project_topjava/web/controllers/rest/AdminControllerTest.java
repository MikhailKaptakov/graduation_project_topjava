package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.Test;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation_project_topjava.TestUtil.userAuth;

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
                .apply(springSecurity())
                .build();
    }

    @Test
    void getNotActual() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(UserTestData.getAdmin()))
                .with(csrf().asHeader()))
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

        ResultActions action = getResultActions(REST_URL, JsonUtil.writeValue(newRestaurant), status().isCreated());

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
    void createIllegalMealWithIdRestaurantWithMeals() throws Exception {
        Meal newMeal = new Meal("illegalNew", 100);
        newMeal.setId((long)1);
        List<Meal> newMeals = List.of(newMeal);
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setMeals(newMeals);
        getResultActions(REST_URL, JsonUtil.writeValue(newRestaurant), status().isUnprocessableEntity());
    }

    @Test
    void createIllegalMealWithNegativePriceRestaurantWithMeals() throws Exception {
        Meal newMeal = new Meal("illegalNew", -100);
        List<Meal> newMeals = List.of(newMeal);
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setMeals(newMeals);
        getResultActions(REST_URL, JsonUtil.writeValue(newRestaurant), status().isUnprocessableEntity());
    }

    @Test
    void createNotNewRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setId((long) 1);
        newRestaurant.setMeals(newMeals);
        getResultActions(REST_URL, JsonUtil.writeValue(newRestaurant), status().isUnprocessableEntity());
    }

    @Test
    void createIllegalRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setName("");
        newRestaurant.setMeals(newMeals);
        getResultActions(REST_URL, JsonUtil.writeValue(newRestaurant), status().isUnprocessableEntity());
    }

    @Test
    void addMealsMenu() throws Exception{
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant notActualRestaurant = RestaurantTestData.getNotActualRestaurant();
        notActualRestaurant.setMeals(newMeals);
        ResultActions action = getResultActions(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID,
                JsonUtil.writeValue(newMeals), status().isCreated());

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);

        notActualRestaurant.setLastUpdateDate(LocalDate.now());
        MealTestData.setMealsId(newMeals, AbstractBaseEntity.START_SEQ);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, notActualRestaurant);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER
                    .assertMatch(dataJpaRestaurantRepository.findById(RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID),
                            notActualRestaurant);
    }

    @Test
    void addIllegalMealWithNegativePriceMenu() throws Exception {
        Meal newMeal = new Meal("illegalNew", -100);
        List<Meal> newMeals = List.of(newMeal);
        Restaurant notActualRestaurant = RestaurantTestData.getNotActualRestaurant();
        notActualRestaurant.setMeals(newMeals);
        getResultActions(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID,
                JsonUtil.writeValue(newMeals),
                status().isUnprocessableEntity());
    }

    private ResultActions getResultActions( String url, String json, ResultMatcher status) throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(UserTestData.getAdmin()))
                .with(csrf().asHeader())
                .content(json))
                .andExpect(status);
        return action;
    }

    //todo рефактор тестов контроллеров, добавить абстрактный класс тестовый
    //todo добавить ридми
    //todo добавить соап юай тесты
    //todo добавить сваггер
}