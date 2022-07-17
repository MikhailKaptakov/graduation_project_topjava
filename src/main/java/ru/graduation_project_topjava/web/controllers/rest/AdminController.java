package ru.graduation_project_topjava.web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.service.RestaurantService;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    static final String REST_URL = "/admin/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping()
    public List<Restaurant> getNotActual() {
        return restaurantService.getAllNotActual();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        return saveRestaurant(restaurant.getMeals(), restaurant);
    }



    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addMealsMenu(@RequestBody List< @Valid Meal> meals, @PathVariable long id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return saveRestaurant(meals, restaurant);
    }

    private ResponseEntity<Restaurant> saveRestaurant(List<Meal> meals, Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.createOrUpdateWithMeals(meals, restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(savedRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(savedRestaurant);
    }
}

