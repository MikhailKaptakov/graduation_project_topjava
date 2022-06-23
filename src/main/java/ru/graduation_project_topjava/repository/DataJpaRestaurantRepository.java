package ru.graduation_project_topjava.repository;

import org.hibernate.annotations.QueryHints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaRestaurantRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final CrudRestaurantRepository restaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew()) {
            return null;
        }
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getNotActualRestaurants()  {

        return findAllNotActual(LocalDate.now());
    }

    public List<Restaurant> getAllActualRestaurants() {

        return findAllActual();
    }

    private  List<Restaurant> findAllActual() {
        LocalDate day = LocalDate.now();
        List<Restaurant> restaurants = entityManager.createQuery("select distinct r from Restaurant r" +
                        " left join fetch r.meals m where r.lastUpdateDate =:day AND m.mealDay =:day", Restaurant.class)
                .setParameter("day", day)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        /*restaurants = entityManager.createQuery(" select distinct r from Restaurant r " +
                        "left join fetch r.votes v where r.lastUpdateDate =:day AND v.voteDate =:day", Restaurant.class)
                .setParameter("day", day)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();*/
        return restaurants;
    }

    private  List<Restaurant> findAllNotActual(@Param("day") LocalDate day) {
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
        return restaurants;
    }


}

