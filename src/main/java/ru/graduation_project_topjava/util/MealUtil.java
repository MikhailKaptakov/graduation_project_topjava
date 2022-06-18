package ru.graduation_project_topjava.util;

import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.to.MealTo;

import java.util.List;

public class MealUtil {

    public static List<MealTo> createMealTosList(List<Meal> meals) {
        return meals.stream().map(MealTo::new).toList();
    }

    public static List<Meal> createMealList(List<MealTo> mealTos) {
        return mealTos.stream().map(MealTo::createNewMeal).toList();
    }
}
