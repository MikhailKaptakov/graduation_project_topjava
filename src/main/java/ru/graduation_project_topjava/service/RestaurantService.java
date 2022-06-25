package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepository restaurantRepository;

    public RestaurantService(DataJpaRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllActual() {
        return restaurantRepository.getAllActual();
    }

    public List<Restaurant> getAllNotActual() {
        return restaurantRepository.getAllNotActual();
    }
}
