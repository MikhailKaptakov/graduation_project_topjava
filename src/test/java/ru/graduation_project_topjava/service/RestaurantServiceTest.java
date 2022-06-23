package ru.graduation_project_topjava.service;

import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.RestaurantTestData;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(TimingExtension.class)
@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @PersistenceContext
    private EntityManager entityManager;


/*    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }*/

    @Test
    void getNotActualRestaurants() {
        List<Restaurant> restaurants  = service.getActualRestaurants();
        /*restaurants.forEach(System.out::println);*/
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurants, RestaurantTestData.RESTAURANT1);
    }

    @Test
    void getActualRestaurants() {
        LocalDate day = LocalDate.now();
        List<Restaurant> restaurants = entityManager.createQuery("select distinct r from Restaurant r" +
                        " left join fetch r.meals m where r.lastUpdateDate <>: day AND m.mealDay =:day", Restaurant.class)
                .setParameter("day", day)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
      /*  restaurants = entityManager.createQuery(" select distinct r from Restaurant r " +
                        "left join fetch r.votes v where r.lastUpdateDate <>:day AND v.voteDate =:day", Restaurant.class)
                .setParameter("day", day)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();*/

        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(restaurants, RestaurantTestData.actualRestaurants);
    }

    @Test
    void addRestaurantMenu() {
    }
}