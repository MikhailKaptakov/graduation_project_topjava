package ru.graduation_project_topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Long> {

/*    @Query("SELECT r from Restaurant r LEFT JOIN FETCH r.meals d LEFT JOIN FETCH r.votes v " +
            " WHERE r.lastUpdateDate =:day AND d.mealDay =:day AND v.voteDate =: day ORDER BY r.name ASC")
    List<Restaurant> findAllActual(@Param("day") LocalDate day);

    @Query("SELECT r from Restaurant r JOIN FETCH r.meals d JOIN FETCH r.votes v " +
            "WHERE r.lastUpdateDate <>:day AND d.mealDay =:day AND v.voteDate =: day ORDER BY r.name ASC")
    List<Restaurant> findAllNotActual(@Param("day") LocalDate day);
    todo удалить  этот код, если новый будет хорошо работать*/
}




