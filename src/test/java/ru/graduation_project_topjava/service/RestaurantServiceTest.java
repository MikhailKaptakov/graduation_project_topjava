package ru.graduation_project_topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.NotEqualException;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    public void getAllActual() {
        List<Restaurant> allActual = service.getAllActual();
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(allActual, RestaurantTestData.actualRestaurant);
    }

    @Test
    public void getAllNotActual() {
        List<Restaurant> allNotActual = service.getAllNotActual();
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(allNotActual, RestaurantTestData.notActualRestaurant);
    }

    @Test
    public void create() {
    /*    User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);*/
    }
}