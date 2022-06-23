package ru.graduation_project_topjava;

import ru.graduation_project_topjava.MatcherFactory;
import ru.graduation_project_topjava.model.AbstractBaseNamedEntity;
import ru.graduation_project_topjava.model.Meal;

import java.util.List;

import static java.time.LocalDateTime.of;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class,
            "restaurant");

    public static final Meal notActualRestaurant1Meal1 = new Meal(3,"old soup", 500, RestaurantTestData.RESTAURANT1);
    public static final Meal notActualRestaurant1Meal2 = new Meal(4, "old tea", 10000, RestaurantTestData.RESTAURANT1);
    public static final Meal actualRestaurant2Meal1 = new Meal(5, "tea", 5000, RestaurantTestData.RESTAURANT2);
    public static final Meal actualRestaurant2Meal2 = new Meal(6, "soup", 30000, RestaurantTestData.RESTAURANT2);
    public static final Meal notActualRestaurant2Meal3 = new Meal(7, "old soup", 30000, RestaurantTestData.RESTAURANT2);
    public static final Meal notActualRestaurant2Meal4 = new Meal(8, "old tea", 5500, RestaurantTestData.RESTAURANT2);

    static {
        notActualRestaurant1Meal1.setMealDay(AbstractBaseNamedEntity.MIN);
        notActualRestaurant1Meal2.setMealDay(AbstractBaseNamedEntity.MIN);
        notActualRestaurant2Meal3.setMealDay(AbstractBaseNamedEntity.MIN);
        notActualRestaurant2Meal4.setMealDay(AbstractBaseNamedEntity.MIN);
    }

    public static final List<Meal> restaurant1Meals = List.of(notActualRestaurant1Meal1, notActualRestaurant1Meal2);

    public static final List<Meal> restaurant2Meals = List.of(actualRestaurant2Meal1, actualRestaurant2Meal2,
            notActualRestaurant2Meal3, notActualRestaurant2Meal4);

    public static final List<Meal> actualRestaurant1Meals = List.of();

    public static final List<Meal> actualRestaurant2Meals = List.of(actualRestaurant2Meal1, actualRestaurant2Meal2);

}
