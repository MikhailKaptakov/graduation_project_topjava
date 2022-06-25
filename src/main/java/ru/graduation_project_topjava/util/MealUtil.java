package ru.graduation_project_topjava.util;

import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.to.MealTo;

import java.util.List;

public class MealUtil {

    private MealUtil() {
    }

    public static List<MealTo> createMealTosList(List<Meal> meals) {
        return meals.stream().map(MealTo::new).toList();
    }
}
