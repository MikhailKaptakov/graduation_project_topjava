package ru.graduation_project_topjava.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaMealRepository {

    private final CrudMealRepository mealRepository;
    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaMealRepository(CrudMealRepository mealRepository, CrudRestaurantRepository userRepository) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = userRepository;
    }

    @Transactional
    public Meal save(Meal meal, long restaurantId) {
        if (!meal.isNew() && get(meal.getId(), restaurantId) == null) {
            return null;
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            return null;
        }
        meal.setRestaurant(restaurant);
        return mealRepository.save(meal);
    }

    @Transactional
    public List<Meal> saveAll(List<Meal> meals, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            return null;
        }
        List<Meal> mealsWithRestaurantId  = meals.stream()
                .peek(meal -> {meal.setRestaurant(restaurant);})
                .toList();
        return mealRepository.saveAll(mealsWithRestaurantId);
    }

    public Meal get(long id, long restaurantId) {
        return mealRepository.findById(id)
                .filter(meal -> meal.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    public List<Meal> getAllActualMeals (long restaurantId) {
        return mealRepository.findAllActual(restaurantId, LocalDate.now());
    }

}
