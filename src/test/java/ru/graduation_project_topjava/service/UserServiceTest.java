package ru.graduation_project_topjava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.util.exception.ConditionFailedException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CrudVoteRepository voteRepository;

    @Test
    void addVoteRevoteTest() {
        if (LocalTime.now().isAfter(UserService.MAX_REVOTE_TIME)) {
            assertThrows(ConditionFailedException.class,
                    () -> {userService.addVote(UserTestData.getUser().getId(),
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
        userService.addVote(UserTestData.getUser2().getId(), RestaurantTestData.getNotActualRestaurant().getId());
        Vote actualVote = voteRepository.getVote(UserTestData.getUser2().getId(), LocalDate.now()).orElse(null);
        Vote expectedVote = new Vote(UserTestData.getUser2(), RestaurantTestData.getNotActualRestaurant());
        expectedVote.setId((long)10000);
        VoteTestData.VOTE_MATCHER.assertMatch(actualVote, expectedVote);
    }
}

   /* NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
        Assertions.assertEquals("Not found entity with id=" + MEAL1_ID, exception.getMessage());*/
    //todo образец кода при ожидании эксепшен в тесте в junit 5. Добавит ьтесты на эксепшн
  /* protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
       assertThrows(rootExceptionClass, () -> {
           try {
               runnable.run();
           } catch (Exception e) {
               throw getRootCause(e);
           }
       });
   }*/