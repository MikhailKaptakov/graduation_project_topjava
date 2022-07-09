package ru.graduation_project_topjava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.util.exception.ConditionFailedException;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CrudVoteRepository voteRepository;

    @BeforeEach
    public void setup() {
        cacheManager.getCache("actual_restaurants").clear();
    }

    @Test
    public void getAllActual() {
        List<Restaurant> allActual = restaurantService.getAllActual();
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(allActual, RestaurantTestData.getActualRestaurant());
    }

    @Test
    public void getAllNotActual() {
        List<Restaurant> allNotActual = restaurantService.getAllNotActual();
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(allNotActual,
                RestaurantTestData.getNotActualRestaurant());
    }

    @Test
    void createWithMeals() {
        Restaurant expectedRestaurant = RestaurantTestData.getNewRestaurant();
        List<Meal> expectedMeals = MealTestData.getNewRestaurantMeals();
        Restaurant actual = restaurantService.createOrUpdateWithMeals(expectedMeals, expectedRestaurant);

        setExpectedMealsParameters(expectedMeals, expectedRestaurant, 1);
        expectedRestaurant.setLastUpdateDate(LocalDate.now());
        expectedRestaurant.setId((long)AbstractBaseEntity.START_SEQ);
        expectedRestaurant.setMeals(expectedMeals);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actual, expectedRestaurant);
    }

        @Test
    void updateWithMeals() {
        List<Meal> expectedMeals = MealTestData.getNewRestaurantMeals();
        Restaurant expectedRestaurant = RestaurantTestData.getNotActualRestaurant();
        Restaurant actualRestaurant = restaurantService.createOrUpdateWithMeals(expectedMeals,
                expectedRestaurant);

        setExpectedMealsParameters(expectedMeals, expectedRestaurant, 0);
        expectedRestaurant.setLastUpdateDate(LocalDate.now());
        expectedRestaurant.setMeals(expectedMeals);

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actualRestaurant, expectedRestaurant);
    }

    @Test
    void addVoteRevoteTest() {
        if (LocalTime.now().isAfter(RestaurantService.MAX_REVOTE_TIME)) {
            assertThrows(ConditionFailedException.class,
                    () -> {restaurantService.addVote(UserTestData.getUser().getId(),
                            RestaurantTestData.getNotActualRestaurant().getId());});
        } else {
            Vote actualVote = voteRepository.getVote(UserTestData.getUser().getId(), LocalDate.now()).orElse(null);
            Vote expectedVote = new Vote(UserTestData.getUser(), RestaurantTestData.getNotActualRestaurant());
            expectedVote.setId((long)152);
            VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
        }
    }

    @Test
    void addNewVote() {
        restaurantService.addVote(UserTestData.getUser2().getId(), RestaurantTestData.getNotActualRestaurant().getId());
        Vote actualVote = voteRepository.getVote(UserTestData.getUser2().getId(), LocalDate.now()).orElse(null);
        Vote expectedVote = new Vote(UserTestData.getUser2(), RestaurantTestData.getNotActualRestaurant());
        expectedVote.setId((long)10000);
        VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
    }

    private void setExpectedMealsParameters(List<Meal> expectedMeals, Restaurant expectedRestaurant,
                                            int addingStartSequenceValue) {
        MealTestData.setMealsId(expectedMeals, AbstractBaseEntity.START_SEQ + addingStartSequenceValue);
        MealTestData.setMealsRestaurant(expectedMeals, expectedRestaurant);
    }
}

