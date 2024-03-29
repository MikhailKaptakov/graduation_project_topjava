package ru.graduation_project_topjava.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Restaurant;

import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class DataJpaRestaurantRepositoryTest {

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Test
    void getAllNotActual() {
        List<Restaurant> restaurants = restaurantRepository.getAllNotActual();
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(restaurants,
                RestaurantTestData.getNotActualRestaurant());
    }

    @Test
    void getAllActual() {
        List<Restaurant> restaurants = restaurantRepository.getAllActual();
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurants,
                RestaurantTestData.getActualWithMealsAndVotes());
    }

    @Test
    void saveAndFlush() {
        Restaurant expected = RestaurantTestData.getNewRestaurant();
        Restaurant actual = restaurantRepository.saveAndFlush(expected);
        expected.setId((long)AbstractBaseEntity.START_SEQ);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void saveAndFlushUpdateRestaurant() {
        Restaurant expected = RestaurantTestData.getActualWithMealsAndVotesRestaurant();
        expected.setName("new name");
        Restaurant actual = restaurantRepository.saveAndFlush(expected);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void findById() {
        Restaurant expected = RestaurantTestData.getNotActualRestaurant();
        Restaurant actual = restaurantRepository.findById(expected.getId());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void findActualById() {
        Restaurant expected = RestaurantTestData.getActualRestaurant();
        Restaurant actual = restaurantRepository.findActualById(expected.getId());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void findActualByIdNotFound() {
        Restaurant expected = RestaurantTestData.getNotActualRestaurant();
        Restaurant actual = restaurantRepository.findActualById(expected.getId());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, null);
    }
}