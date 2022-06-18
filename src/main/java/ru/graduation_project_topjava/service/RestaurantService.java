package ru.graduation_project_topjava.service;

import org.springframework.util.Assert;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaMealRepository;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.to.MealTo;
import ru.graduation_project_topjava.to.RestaurantTo;
import ru.graduation_project_topjava.util.MealUtil;
import ru.graduation_project_topjava.util.RestaurantUtil;
import ru.graduation_project_topjava.util.ValidationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<RestaurantTo> getActualRestaurants() {
        return RestaurantUtil.createActualRestaurantTosList(restaurantRepository.getAllActualRestaurants());
    }

    public Restaurant createRestaurantWithMenu(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "Restaurant must not be null");
        List<MealTo> mealTos = restaurantTo.getMealTos();
        Assert.notNull(mealTos, "Meals menu must not be null");
        checkMealTosList(mealTos);
        Restaurant restaurant = restaurantTo.createNewRestaurant();
        return restaurantRepository.save(restaurant);
    }

    public void addRestaurantMenu(List<MealTo> mealTos, long  restaurantId) {
        Assert.notNull(mealTos, "Meals menu must not be null");
        checkMealTosList(mealTos);
        List<Meal> meals = MealUtil.createMealList(mealTos);
        mealRepository.saveAll(meals, restaurantId);
    }

    private void checkMealTosList(List<MealTo> mealTos) {
        checkMealTosToEmptyList(mealTos);
        checkMealTosListToDuplicate(mealTos);
    }

    private void checkMealTosToEmptyList(List<MealTo> mealTos) {
        if (mealTos.size() == 0) {
            throw new IllegalArgumentException("Meal list is empty");
        }
    }

    private void checkMealTosListToDuplicate(List<MealTo> mealTos) {
        Map<String, MealTo> mealTosMap = new HashMap<>();
        for (MealTo mealTo : mealTos) {
            if (mealTosMap.containsKey(mealTo.getName())) {
                throw new IllegalArgumentException("Meal name (" + mealTo.getName() + ") is duplicate");
            }
            mealTosMap.put(mealTo.getName(), mealTo);
        }
    }
 //todo перенести  преобразование в ТО на слой контроллеров
    //проверка получаемых значений
}
