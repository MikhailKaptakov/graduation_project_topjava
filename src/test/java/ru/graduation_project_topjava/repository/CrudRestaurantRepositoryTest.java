package ru.graduation_project_topjava.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class CrudRestaurantRepositoryTest {

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Test
    void findAllByDate() {
        List<Restaurant> restaurants = restaurantRepository.findAllByDate(LocalDate.now());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(restaurants,
                RestaurantTestData.getActualWithMealsAndVotes());
    }

    @Test
    void findAllWithoutDate() {
        List<Restaurant> restaurants = restaurantRepository.findAllWithoutDate(LocalDate.now());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(restaurants, RestaurantTestData.getNotActualRestaurant());
    }


    @Test
    void findActualById() {
        Restaurant restaurant = restaurantRepository.findByIdByDate(RestaurantTestData.ACTUAL_RESTAURANT_ID, LocalDate.now())
                .orElse(null);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(restaurant, RestaurantTestData.getActualRestaurant());
    }
}