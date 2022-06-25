package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.to.RestaurantTo;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


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

    public static final Restaurant notActualRestaurant = new Restaurant();
    public static final Restaurant actualRestaurant = new Restaurant("ActualRestaurant", MealTestData.actualRestaurantActualMeals);

    static {
        notActualRestaurant.setId(NOT_ACTUAL_RESTAURANT_ID);
        notActualRestaurant.setName("NotActualRestaurant");
        notActualRestaurant.setLastUpdateDate(Restaurant.MIN);
        actualRestaurant.setId(ACTUAL_RESTAURANT_ID);
        actualRestaurant.setLastUpdateDate(LocalDate.now());
        actualRestaurant.setVotes(VoteTestData.actualRestaurant2Votes);
    }

}
