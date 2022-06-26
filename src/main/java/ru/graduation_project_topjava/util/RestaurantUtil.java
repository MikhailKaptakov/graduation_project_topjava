package ru.graduation_project_topjava.util;

import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.to.RestaurantTo;

import java.util.List;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static List<RestaurantTo> createActualRestaurantTosList(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> {
            return new RestaurantTo(restaurant);
        }).toList();
    }
}
