package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.repository.DataJpaMealRepository;
import ru.graduation_project_topjava.to.MealTo;
import ru.graduation_project_topjava.util.MealUtil;
import ru.graduation_project_topjava.util.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MealService {

    private final DataJpaMealRepository mealRepository;

    public MealService(DataJpaMealRepository repository) {
        this.mealRepository = repository;
    }

    public List<MealTo> getAllActual(long restaurantId) {
        List<Meal> meals = mealRepository.getAllActualMeals(restaurantId);
        if (meals == null || meals.size()  == 0)  {
            return null;
        }
        return MealUtil.createMealTosList(mealRepository.getAllActualMeals(restaurantId));
    }


}
