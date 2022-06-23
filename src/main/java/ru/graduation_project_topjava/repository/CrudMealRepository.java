package ru.graduation_project_topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT m from Meal m WHERE m.restaurant.id=:restaurantId AND m.mealDay =:day ORDER BY m.name ASC")
    List<Meal> findAllActual(@Param("restaurantId") long restaurantId, @Param("day")LocalDate day);
}


