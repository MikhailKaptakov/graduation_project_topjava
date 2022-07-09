package ru.graduation_project_topjava.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class CrudMealRepositoryTest {

    @Autowired
    private CrudMealRepository mealRepository;

    @Test
    void findAllByDate() {
        List<Meal> meals = mealRepository.findAllByDate(LocalDate.now());
        MealTestData.MEAL_MATCHER.assertMatch(meals, MealTestData.getAllActualMeals());
    }
}