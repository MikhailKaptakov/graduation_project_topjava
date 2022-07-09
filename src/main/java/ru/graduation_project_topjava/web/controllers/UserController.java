package ru.graduation_project_topjava.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.service.RestaurantService;
import ru.graduation_project_topjava.service.UserService;
import ru.graduation_project_topjava.service.VoteService;
import ru.graduation_project_topjava.web.SecurityUtil;
//import ru.graduation_project_topjava.web.SecurityUtil;

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

    @Autowired
    private VoteService voteService;

    @GetMapping()
    public List<Restaurant> getActual() {
        return restaurantService.getAllActual();
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@PathVariable long id) {
        Long userId = authUserId();
        log.debug(Long.toString(userId));
        Vote created = restaurantService.addVote(userId, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/votes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/votes" + "/{id}")
    public Vote getUserVote(@PathVariable long id) {
        Long userId = authUserId();
        return voteService.getVote(id, userId);
    }


    //todo добавить валидацию данных
    //todo добавить обработку ошибок
    //todo добавить спринг кеш, там где нужно, допустим - получение списка ресторанов
    //todo разобраться с аутентификацией в тестах спринг секьюрити
    //todo придумать как в тесте подменять проверку времени
    //todo добавить тесты с невалидными данными
    //todo добавить кеш хибернейта 2-го уровня

}
