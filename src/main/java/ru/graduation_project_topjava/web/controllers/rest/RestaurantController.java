package ru.graduation_project_topjava.web.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.service.RestaurantService;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    public static final String REST_URL = "/restaurants";

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/{id}")
    public Restaurant getRestaurant (@PathVariable long id) {
        log.debug("get Restaurant: " + id);
        return restaurantService.getRestaurant(id);
    }
}
