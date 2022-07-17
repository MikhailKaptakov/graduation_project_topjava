package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,
                    "meals.restaurant", "votes.user", "votes.restaurant");

    public static final MatcherFactory.Matcher<Restaurant> IGNORE_FIELDS_RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "meals", "votes");

    public static final long NOT_ACTUAL_RESTAURANT_ID = 1;
    public static final long ACTUAL_RESTAURANT_ID = 2;
    public static final long ACTUAL_RESTAURANT2_ID = 3;

    private static final Restaurant notActualRestaurant = new Restaurant("NotActualRestaurant");
    private static final Restaurant actualRestaurant = new Restaurant("ActualRestaurant");
    private static final Restaurant actualRestaurant2 = new Restaurant("ActualRestaurant2");
    private static final Restaurant newRestaurant = new Restaurant("NewRestaurant");

    static {
        notActualRestaurant.setId(NOT_ACTUAL_RESTAURANT_ID);
        actualRestaurant.setId(ACTUAL_RESTAURANT_ID);
        actualRestaurant.setLastUpdateDate(LocalDate.now());
        newRestaurant.setLastUpdateDate(Restaurant.MIN);
        actualRestaurant2.setId(ACTUAL_RESTAURANT2_ID);
        actualRestaurant2.setLastUpdateDate(LocalDate.now());
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant(newRestaurant);
    }

    public static Restaurant getActualWithMealsAndVotesRestaurant() {
        Restaurant restaurant = new Restaurant(actualRestaurant);
        restaurant.setMeals(MealTestData.getActualRestaurantActualMeals());
        restaurant.setVotes(VoteTestData.getActualRestaurantVotes());
        return restaurant;
    }

    public static Restaurant getActualWithMealsAndVotesRestaurant2() {
        Restaurant restaurant = new Restaurant(actualRestaurant2);
        restaurant.setMeals(MealTestData.getActualRestaurant2ActualMeals());
        return restaurant;
    }

    public static List<Restaurant> getActualWithMealsAndVotes() {
        return List.of(getActualWithMealsAndVotesRestaurant(), getActualWithMealsAndVotesRestaurant2());
    }

    public static Restaurant getActualRestaurant() {
        return new Restaurant(actualRestaurant);
    }

    public static Restaurant getActualRestaurant2() {
        return new Restaurant(actualRestaurant2);
    }

    public static Restaurant getNotActualRestaurant() {
        return new Restaurant(notActualRestaurant);
    }


}
