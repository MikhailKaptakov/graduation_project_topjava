package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.to.RestaurantTo;

import java.time.LocalDate;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,
                    "meals.restaurant", "votes.user", "votes.restaurant");

    public static final MatcherFactory.Matcher<Restaurant> IGNORE_FIELDS_RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "meals", "votes");

    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final long NOT_ACTUAL_RESTAURANT_ID = 1;
    public static final long ACTUAL_RESTAURANT_ID = 2;

    private static final Restaurant notActualRestaurant = new Restaurant("NotActualRestaurant");
    private static final Restaurant actualRestaurant = new Restaurant("ActualRestaurant");
    private static final Restaurant newRestaurant = new Restaurant("NewRestaurant");

    static {
        notActualRestaurant.setId(NOT_ACTUAL_RESTAURANT_ID);
        actualRestaurant.setId(ACTUAL_RESTAURANT_ID);
        actualRestaurant.setLastUpdateDate(LocalDate.now());
        actualRestaurant.setMeals(MealTestData.getActualRestaurantActualMeals());
        actualRestaurant.setVotes(VoteTestData.getActualRestaurant2Votes());
        newRestaurant.setLastUpdateDate(Restaurant.MIN);
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(newRestaurant);
    }

    public static Restaurant getActualRestaurant() {
        return new Restaurant(actualRestaurant);
    }

    public static Restaurant getNotActualRestaurant() {
        return new Restaurant(notActualRestaurant);
    }
}
