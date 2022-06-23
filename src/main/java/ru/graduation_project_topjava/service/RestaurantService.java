package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaMealRepository;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepository restaurantRepository;

    private final DataJpaMealRepository mealRepository;

    public RestaurantService(DataJpaRestaurantRepository restaurantRepository,
                             DataJpaMealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository  =  mealRepository;
    }

    public List<Restaurant> getNotActualRestaurants() {
        return restaurantRepository.getNotActualRestaurants();
    }

    public List<Restaurant> getActualRestaurants() {
        return restaurantRepository.getAllActualRestaurants();
    }

    public Restaurant createRestaurantWithMenu(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        List<Meal> meals = restaurant.getMeals();
        Assert.notNull(meals, "Meals menu must not be null");
        checkMealTosList(meals);
        return restaurantRepository.save(restaurant);
    }

    public void addRestaurantMenu(List<Meal> meals, long  restaurantId) {
        Assert.notNull(meals, "Meals menu must not be null");
        checkMealTosList(meals);
        mealRepository.saveAll(meals, restaurantId);
    }

    private void checkMealTosList(List<Meal> meals) {
        checkMealEmptyList(meals);
        checkMealListToDuplicate(meals);
    }

    private void checkMealEmptyList(List<Meal> meals) {
        if (meals.size() == 0) {
            throw new IllegalArgumentException("Meal list is empty");
        }
    }

    private void checkMealListToDuplicate(List<Meal> meals) {
        Map<String, Meal> mealMap = new HashMap<>();
        for (Meal meal : meals) {
            if (mealMap.containsKey(meal.getName())) {
                throw new IllegalArgumentException("Meal name (" + meal.getName() + ") is duplicate");
            }
            mealMap.put(meal.getName(), meal);
        }
    }
 //todo перенести  преобразование в ТО на слой контроллеров
    //проверка получаемых значений
}
