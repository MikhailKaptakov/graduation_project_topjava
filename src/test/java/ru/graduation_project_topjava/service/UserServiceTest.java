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

    @Test
    void create() {
        //todo
    }

    @Test
    void loadUserByUsername() {
        //todo
    }

    @Test
    void getUser() {
        //todo
    }

}