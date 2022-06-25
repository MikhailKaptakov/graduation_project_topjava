package ru.graduation_project_topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Meal;
import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v WHERE v.voteDate =:date ORDER BY v.id ASC")
    List<Vote> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id =:user_id AND v.voteDate =: date")
    Optional<Vote> getVote(@Param("user_id") Long userId, @Param("date") LocalDate date);
}