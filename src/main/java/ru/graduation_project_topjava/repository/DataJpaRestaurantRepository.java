package ru.graduation_project_topjava.repository;

import org.springframework.stereotype.Repository;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataJpaRestaurantRepository {

    private final CrudRestaurantRepository restaurantRepository;
    private final CrudMealRepository mealRepository;
    private final CrudVoteRepository voteRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository restaurantRepository,
                                       CrudMealRepository mealRepository,
                                       CrudVoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.voteRepository = voteRepository;
    }

    public Restaurant saveAndFlush(Restaurant restaurant) {
        return restaurantRepository.saveAndFlush(restaurant);
    }

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public List<Restaurant> getAllNotActual() {
        return restaurantRepository.findAllWithoutDate(LocalDate.now());
    }

    public List<Restaurant> getAllActual() {
        LocalDate actualDate = LocalDate.now();
        List<Restaurant> restaurants = restaurantRepository.findAllByDate(actualDate);
        List<Meal> meals = mealRepository.findAllByDate(actualDate);
        List<Vote> votes = voteRepository.findAllByDate(actualDate);

        Map<Long, Restaurant> restaurantMap = getRestaurantMap(restaurants);
        setMeals(restaurantMap, meals);
        setVotes(restaurantMap, votes);
        return restaurants;
    }

    private Map<Long, Restaurant> getRestaurantMap(List<Restaurant> restaurants) {
        Map<Long, Restaurant> restaurantMap = new HashMap<>();
        for (Restaurant restaurant : restaurants) {
            restaurantMap.put(restaurant.getId(), restaurant);
        }
        return restaurantMap;
    }

    private void setMeals(Map<Long, Restaurant> restaurantMap, List<Meal> meals) {
        for (Meal meal : meals) {
            Long restaurantId = meal.getRestaurantId();
            if (restaurantMap.containsKey(restaurantId)) {
                restaurantMap.get(restaurantId).addMeal(meal);
            }
        }
    }

    private void setVotes(Map<Long, Restaurant> restaurantMap, List<Vote> votes) {
        for (Vote vote : votes) {
            Long restaurantId = vote.getRestaurantId();
            if (restaurantMap.containsKey(restaurantId)) {
                restaurantMap.get(restaurantId).addVote(vote);
            }
        }
    }
}

