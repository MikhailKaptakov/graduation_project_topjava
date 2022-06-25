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

    @Query("SELECT r FROM Restaurant r WHERE r.lastUpdateDate =:date ORDER BY r.id ASC")
    List<Restaurant> findAllByDate(@Param("date")LocalDate date);

    @Query("SELECT r FROM Restaurant r WHERE r.lastUpdateDate <>:date ORDER BY r.id ASC")
    List<Restaurant> findAllWithoutDate(@Param("date")LocalDate date);
}




