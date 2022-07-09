package ru.graduation_project_topjava.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudMealRepository;
import ru.graduation_project_topjava.repository.CrudUserRepository;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.util.MealUtil;
import ru.graduation_project_topjava.util.exception.ConditionFailedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    public static final LocalTime MAX_REVOTE_TIME = LocalTime.of(11,00);
    //todo придумать способ тестировать с заменой времени

    private final DataJpaRestaurantRepository restaurantRepository;
    private final CrudMealRepository mealRepository;
    private final CrudVoteRepository voteRepository;
    private final UserService userService;

    public RestaurantService(DataJpaRestaurantRepository restaurantRepository,
                             CrudMealRepository mealRepository,
                             CrudVoteRepository voteRepository,
                             UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.voteRepository = voteRepository;
        this.userService = userService;
    }

    @Cacheable("actual_restaurants")
    public List<Restaurant> getAllActual() {
        return restaurantRepository.getAllActual();
    }

    public List<Restaurant> getAllNotActual() {
        return restaurantRepository.getAllNotActual();
    }

    @Transactional
    public Vote addVote(Long userId, Long restaurantId) {
        Vote vote = voteRepository.getVote(userId, LocalDate.now()).orElse(null);
        if (vote != null) {
            if (LocalTime.now().isAfter(MAX_REVOTE_TIME)) {
                throw new ConditionFailedException("Time over 11 hours. You can't revote");
            }
            Restaurant restaurant = restaurantRepository.findById(restaurantId);
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        }
        User user = userService.getUser(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        vote = new Vote(user, restaurant);
        return voteRepository.save(vote);
    }

    @Transactional
    @CacheEvict(value = "actual_restaurants", allEntries = true)
    public Restaurant createOrUpdateWithMeals(List<Meal> newActualMeals, Restaurant restaurant) {
        MealUtil.checkMealsIsNew(newActualMeals);
        Restaurant restaurantWithUpdatedLastUpdateDateField;
        if (!restaurant.itsNew()) {
            restaurantWithUpdatedLastUpdateDateField = restaurantRepository.findById(restaurant.getId());
            restaurantWithUpdatedLastUpdateDateField.setLastUpdateDate(LocalDate.now());
        } else {
            restaurantWithUpdatedLastUpdateDateField = getUpdatedRestaurant(restaurant);
        }

        Restaurant savedRestaurant = restaurantRepository.saveAndFlush(restaurantWithUpdatedLastUpdateDateField);

        List<Meal> mealsWithUpdatedRestaurantField = getUpdatedMeals(newActualMeals, savedRestaurant);
        List<Meal> savedMeals = mealRepository.saveAll(mealsWithUpdatedRestaurantField);

        savedRestaurant.setMeals(savedMeals);
        return savedRestaurant;
    }

    private Restaurant getUpdatedRestaurant(Restaurant restaurant) {
        Restaurant updateDateRestaurant = new Restaurant(restaurant);
        updateDateRestaurant.setLastUpdateDate(LocalDate.now());
        return updateDateRestaurant;
    }

    private List<Meal> getUpdatedMeals(List<Meal> meals, Restaurant restaurant) {
        List<Meal> updatedMeals = new ArrayList<>();
        for (Meal meal : meals) {
            Meal updatedMeal = new Meal(meal);
            updatedMeal.setRestaurant(restaurant);
            updatedMeals.add(updatedMeal);
        }
        return updatedMeals;
    }

}
