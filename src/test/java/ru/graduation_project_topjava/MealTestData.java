package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.AbstractBaseNamedEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;

import java.util.List;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class,
            "restaurant");

    public static long FIRST_MEAL_ID = 20;

    private static final Meal notActualRestaurantMeal1 = new Meal("old soup", 500);
    private static final Meal notActualRestaurantMeal2 = new Meal("old tea", 10000);
    private static final Meal actualRestaurantActualMeal1 = new Meal("tea", 5000);
    private static final Meal actualRestaurantActualMeal2 = new Meal("soup", 30000);
    private static final Meal actualRestaurantNotActualMeal3 = new Meal("old soup", 30000);
    private static final Meal actualRestaurantNotActualMeal4 = new Meal("old tea", 5500);

    private static final Meal newMeal1 = new Meal("first dish", 2000);
    private static final Meal newMeal2 = new Meal("second dish", 5000);
    private static final Meal newMeal3 = new Meal("juice", 1000);

    static {
        notActualRestaurantMeal1.setId(FIRST_MEAL_ID);
        notActualRestaurantMeal2.setId(FIRST_MEAL_ID + 1);
        actualRestaurantActualMeal1.setId(FIRST_MEAL_ID + 2);
        actualRestaurantActualMeal2.setId(FIRST_MEAL_ID + 3);
        actualRestaurantNotActualMeal3.setId(FIRST_MEAL_ID + 4);
        actualRestaurantNotActualMeal4.setId(FIRST_MEAL_ID + 5);
        notActualRestaurantMeal1.setRestaurant(RestaurantTestData.getNotActualRestaurant());
        notActualRestaurantMeal2.setRestaurant(RestaurantTestData.getNotActualRestaurant());
        actualRestaurantActualMeal1.setRestaurant(RestaurantTestData.getActualRestaurant());
        actualRestaurantActualMeal2.setRestaurant(RestaurantTestData.getActualRestaurant());
        actualRestaurantNotActualMeal3.setRestaurant(RestaurantTestData.getActualRestaurant());
        actualRestaurantNotActualMeal4.setRestaurant(RestaurantTestData.getActualRestaurant());
        notActualRestaurantMeal1.setMealDate(AbstractBaseNamedEntity.MIN);
        notActualRestaurantMeal2.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal3.setMealDate(AbstractBaseNamedEntity.MIN);
        actualRestaurantNotActualMeal4.setMealDate(AbstractBaseNamedEntity.MIN);
    }

    public static List<Meal> getActualRestaurantActualMeals() {
        return List.of(new Meal(actualRestaurantActualMeal1),
                new Meal(actualRestaurantActualMeal2));
    }

    public static List<Meal> getAllActualMeals() {
        return List.of(new Meal(actualRestaurantActualMeal1),
                new Meal(actualRestaurantActualMeal2));
    }

    public static List<Meal> getNewRestaurantMeals() {
        return List.of(new Meal(newMeal1),
                new Meal(newMeal2), new Meal(newMeal3));
    }

    public static void setMealsId(List<Meal> meals, int startSequenceValue) {
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            long newId = i + startSequenceValue;
            meal.setId(newId);
        }
    }

    public static void setMealsRestaurant(List<Meal> meals, Restaurant restaurant) {
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            meal.setRestaurant(restaurant);
        }
    }

}
