package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.CrudMealRepository;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final CrudMealRepository mealRepository;
    private final DataJpaRestaurantRepository restaurantRepository;

    public MealService(CrudMealRepository mealRepository, DataJpaRestaurantRepository restaurantRepository) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public List<Meal> createMealsRestaurantMenu(List<Meal> meals, Restaurant restaurant) {
        ValidationUtil.checkHaveId(restaurant);
        meals.forEach((m)->{
            ValidationUtil.checkNew(m);
            m.setRestaurant(restaurant);
        });
        restaurant.setLastUpdateDate(LocalDate.now());
        restaurantRepository.save(restaurant);
        return mealRepository.saveAll(meals);
    }



}
