package ru.graduation_project_topjava.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.service.RestaurantService;
import ru.graduation_project_topjava.service.UserService;
//import ru.graduation_project_topjava.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    static final String REST_URL = "/user/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<Restaurant> getActual() {
        return restaurantService.getAllActual();
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@PathVariable long id) {
        //Long userId = SecurityUtil.authUserId(); //todo add security
        Long userId = (long)100;
        Vote created = userService.addVote(userId, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/votes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    //todo добавить валидацию данных
    //todo добавить обработку ошибок
    //todo добавить спринг кеш, там где нужно, допустим - получение списка ресторанов
    //todo добавить спринг секьюрити и соответственно изменить тесты
    //todo придумать как в тесте подменять проверку времени
    //todo добавить тесты с невалидными данными
    //todo добавить кеш хибернейта 2-го уровня

}
