package ru.graduation_project_topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


class JsonUtilTest {

    @Test
    void readWriteValue() {
        Restaurant expected = RestaurantTestData.getNotActualRestaurant();
        String json = JsonUtil.writeValue(expected);
        System.out.println(json);
        Restaurant actual = JsonUtil.readValue(json, Restaurant.class);
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void readWriteValues() {
        List<Restaurant> expected = RestaurantTestData.getActualWithMealsAndVotes();
        String json = JsonUtil.writeValue(expected);
        System.out.println(json);
        List<Restaurant> actual = JsonUtil.readValues(json, Restaurant.class);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actual, expected);
    }
}