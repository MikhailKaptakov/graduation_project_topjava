package ru.graduation_project_topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.model.Restaurant;

import java.util.List;

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