package ru.graduation_project_topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.util.Assert;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Test
    void createMealsRestaurantMenu() {
        List<Meal> expected = MealTestData.getNewNotActualRestaurantMeals();
        List<Meal> actual = mealService.createMealsRestaurantMenu(expected,
                RestaurantTestData.notActualRestaurant);
        setIdToMealList(expected);
        MealTestData.MEAL_MATCHER.assertMatch(actual, expected);
        assertUpdatedRestaurant();
    }

    private void setIdToMealList(List<Meal> meals) {
        for (int i = 0; i < meals.size(); i++) {
            meals.get(i).setId((long) (i + AbstractBaseEntity.START_SEQ));
        }
    }

    private void assertUpdatedRestaurant() {
        Restaurant actualRestaurant = restaurantRepository.findById(RestaurantTestData.notActualRestaurant.getId());
        Assert.notNull(actualRestaurant, "restaurant not found");
        Restaurant expectedRestaurant = new Restaurant(RestaurantTestData.notActualRestaurant);
        expectedRestaurant.setLastUpdateDate(LocalDate.now());
        RestaurantTestData.IGNORE_FIELDS_RESTAURANT_MATCHER.assertMatch(actualRestaurant,expectedRestaurant);
    }



/*    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }*/


}