package ru.graduation_project_topjava.util;

import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

import java.util.List;

public class MealUtil {

    public static void checkMealsIsNew(List<Meal> meals) {
        meals.forEach(ValidationUtil::checkNew);
    }

}
