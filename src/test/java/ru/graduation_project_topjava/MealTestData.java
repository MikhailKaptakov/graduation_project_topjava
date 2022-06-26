package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.AbstractBaseNamedEntity;
import ru.graduation_project_topjava.model.Meal;

import java.util.List;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class,
            "restaurant");

    public static long FIRST_MEAL_ID = 20;

    public static final Meal notActualRestaurantMeal1 = new Meal("old soup", 500);
    public static final Meal notActualRestaurantMeal2 = new Meal("old tea", 10000);
    public static final Meal actualRestaurantActualMeal1 = new Meal("tea", 5000);
    public static final Meal actualRestaurantActualMeal2 = new Meal("soup", 30000);
    public static final Meal actualRestaurantNotActualMeal3 = new Meal("old soup", 30000);
    public static final Meal actualRestaurantNotActualMeal4 = new Meal("old tea", 5500);

    public static final Meal newMeal1NotActualRestaurant = new Meal("first dish", 2000);
    public static final Meal newMeal2NotActualRestaurant = new Meal("second dish", 5000);
    public static final Meal newMeal3NotActualRestaurant = new Meal("juice", 1000);

    static {
        notActualRestaurantMeal1.setId(FIRST_MEAL_ID);
        notActualRestaurantMeal2.setId(FIRST_MEAL_ID + 1);
        actualRestaurantActualMeal1.setId(FIRST_MEAL_ID + 2);
        actualRestaurantActualMeal2.setId(FIRST_MEAL_ID + 3);
        actualRestaurantNotActualMeal3.setId(FIRST_MEAL_ID + 4);
        actualRestaurantNotActualMeal4.setId(FIRST_MEAL_ID + 5);
        notActualRestaurantMeal1.setRestaurant(RestaurantTestData.notActualRestaurant);
        notActualRestaurantMeal2.setRestaurant(RestaurantTestData.notActualRestaurant);
        actualRestaurantActualMeal1.setRestaurant(RestaurantTestData.actualRestaurant);
        actualRestaurantActualMeal2.setRestaurant(RestaurantTestData.actualRestaurant);
        actualRestaurantNotActualMeal3.setRestaurant(RestaurantTestData.actualRestaurant);
        actualRestaurantNotActualMeal4.setRestaurant(RestaurantTestData.actualRestaurant);
        newMeal1NotActualRestaurant.setRestaurant(RestaurantTestData.notActualRestaurant);
        newMeal2NotActualRestaurant.setRestaurant(RestaurantTestData.notActualRestaurant);
        newMeal3NotActualRestaurant.setRestaurant(RestaurantTestData.notActualRestaurant);
        notActualRestaurantMeal1.setMealDate(AbstractBaseNamedEntity.MIN);
        notActualRestaurantMeal2.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal3.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal4.setMealDate(AbstractBaseNamedEntity.MIN);
    }

    public static final List<Meal> actualRestaurantActualMeals = List.of(actualRestaurantActualMeal1, actualRestaurantActualMeal2);

    public static final List<Meal> allActualMeals = List.of(actualRestaurantActualMeal1, actualRestaurantActualMeal2);

    public static List<Meal> getNewNotActualRestaurantMeals() {
        return List.of(newMeal1NotActualRestaurant,
                newMeal2NotActualRestaurant, newMeal3NotActualRestaurant);
    }

}
