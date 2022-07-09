package ru.graduation_project_topjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("actual_restaurants").clear();
    }

    @Test
    public void getAllActual() {
        List<Restaurant> allActual = restaurantService.getAllActual();
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(allActual, RestaurantTestData.getActualRestaurant());
    }

    @Test
    public void getAllNotActual() {
        List<Restaurant> allNotActual = restaurantService.getAllNotActual();
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(allNotActual,
                RestaurantTestData.getNotActualRestaurant());
    }

    @Test
    void createWithMeals() {
        Restaurant expectedRestaurant = RestaurantTestData.getNewRestaurant();
        List<Meal> expectedMeals = MealTestData.getNewRestaurantMeals();
        Restaurant actual = restaurantService.createOrUpdateWithMeals(expectedMeals, expectedRestaurant);

        setExpectedMealsParameters(expectedMeals, expectedRestaurant, 1);
        expectedRestaurant.setLastUpdateDate(LocalDate.now());
        expectedRestaurant.setId((long)AbstractBaseEntity.START_SEQ);
        expectedRestaurant.setMeals(expectedMeals);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actual, expectedRestaurant);
    }

        @Test
    void updateWithMeals() {
        List<Meal> expectedMeals = MealTestData.getNewRestaurantMeals();
        Restaurant expectedRestaurant = RestaurantTestData.getNotActualRestaurant();
        Restaurant actualRestaurant = restaurantService.createOrUpdateWithMeals(expectedMeals,
                expectedRestaurant);

        setExpectedMealsParameters(expectedMeals, expectedRestaurant, 0);
        expectedRestaurant.setLastUpdateDate(LocalDate.now());
        expectedRestaurant.setMeals(expectedMeals);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actualRestaurant, expectedRestaurant);
    }

    private void setExpectedMealsParameters(List<Meal> expectedMeals, Restaurant expectedRestaurant,
                                            int addingStartSequenceValue) {
        MealTestData.setMealsId(expectedMeals, AbstractBaseEntity.START_SEQ + addingStartSequenceValue);
        MealTestData.setMealsRestaurant(expectedMeals, expectedRestaurant);
    }
}

