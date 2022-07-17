package ru.graduation_project_topjava.web.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static ru.graduation_project_topjava.web.SecurityUtil.*;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String REST_URL = "/user/restaurants";

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping()
    public List<Restaurant> getActual() {
        log.debug("getActual");
        return restaurantService.getAllActual();
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@PathVariable long id) {
        Long userId = authUserId();
        log.debug(Long.toString(userId));
        Vote newVote = restaurantService.addVote(userId, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/votes" + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }
}
