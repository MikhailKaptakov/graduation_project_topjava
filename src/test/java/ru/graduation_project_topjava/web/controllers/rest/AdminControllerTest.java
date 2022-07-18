package ru.graduation_project_topjava.web.controllers.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
class AdminControllerTest extends AbstractRestControllerTest {

    private static final String REST_URL = AdminController.REST_URL + "/";

    @Autowired
    private DataJpaRestaurantRepository dataJpaRestaurantRepository;

    @Test
    void getNotActual() throws Exception {
        getResultActions(REST_URL, status().isOk(), UserTestData.getAdmin())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER
                        .contentJson(List.of(RestaurantTestData.getNotActualRestaurant())));
    }

    @Test
    void createRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setMeals(newMeals);

        ResultActions action = postResultActions(REST_URL, JsonUtil.writeValue(newRestaurant),
                status().isCreated(), UserTestData.getAdmin());

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
        postResultActions(REST_URL, JsonUtil.writeValue(newRestaurant),
                status().isUnprocessableEntity(), UserTestData.getAdmin());
    }

    @Test
    void createIllegalMealWithNegativePriceRestaurantWithMeals() throws Exception {
        Meal newMeal = new Meal("illegalNew", -100);
        List<Meal> newMeals = List.of(newMeal);
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setMeals(newMeals);
        postResultActions(REST_URL, JsonUtil.writeValue(newRestaurant),
                status().isUnprocessableEntity(), UserTestData.getAdmin());
    }

    @Test
    void createNotNewRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setId((long) 1);
        newRestaurant.setMeals(newMeals);
        postResultActions(REST_URL, JsonUtil.writeValue(newRestaurant),
                status().isUnprocessableEntity(), UserTestData.getAdmin());
    }

    @Test
    void createIllegalRestaurantWithMeals() throws Exception {
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant newRestaurant = RestaurantTestData.getNewRestaurant();
        newRestaurant.setName("");
        newRestaurant.setMeals(newMeals);
        postResultActions(REST_URL, JsonUtil.writeValue(newRestaurant),
                status().isUnprocessableEntity(), UserTestData.getAdmin());
    }

    @Test
    void addMealsMenu() throws Exception{
        List<Meal> newMeals = MealTestData.getNewRestaurantMeals();
        Restaurant notActualRestaurant = RestaurantTestData.getNotActualRestaurant();
        notActualRestaurant.setMeals(newMeals);
        ResultActions action = postResultActions(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID,
                JsonUtil.writeValue(newMeals), status().isCreated(), UserTestData.getAdmin());

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
        postResultActions(REST_URL + RestaurantTestData.NOT_ACTUAL_RESTAURANT_ID,
                JsonUtil.writeValue(newMeals), status().isUnprocessableEntity(), UserTestData.getAdmin());
    }

    //todo рефактор тестов контроллеров, добавить абстрактный класс тестовый
    //todo добавить ридми
    //todo добавить соап юай тесты
    //todo добавить сваггер
}