package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.AbstractBaseNamedEntity;
import ru.graduation_project_topjava.model.Meal;

import java.util.List;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class,
            "restaurant");

    public static final Meal notActualRestaurantMeal1 = new Meal(20,"old soup", 500, RestaurantTestData.notActualRestaurant);
    public static final Meal notActualRestaurantMeal2 = new Meal(21, "old tea", 10000, RestaurantTestData.notActualRestaurant);
    public static final Meal actualRestaurantActualMeal1 = new Meal(22, "tea", 5000, RestaurantTestData.actualRestaurant);
    public static final Meal actualRestaurantActualMeal2 = new Meal(23, "soup", 30000, RestaurantTestData.actualRestaurant);
    public static final Meal actualRestaurantNotActualMeal3 = new Meal(24, "old soup", 30000, RestaurantTestData.actualRestaurant);
    public static final Meal actualRestaurantNotActualMeal4 = new Meal(25, "old tea", 5500, RestaurantTestData.actualRestaurant);

    static {
        notActualRestaurantMeal1.setMealDate(AbstractBaseNamedEntity.MIN);
        notActualRestaurantMeal2.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal3.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal4.setMealDate(AbstractBaseNamedEntity.MIN);
    }

    public static final List<Meal> actualRestaurantActualMeals = List.of(actualRestaurantActualMeal1, actualRestaurantActualMeal2);

    public static final List<Meal> allActualMeals = List.of(actualRestaurantActualMeal1, actualRestaurantActualMeal2);

}
