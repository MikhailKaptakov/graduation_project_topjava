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