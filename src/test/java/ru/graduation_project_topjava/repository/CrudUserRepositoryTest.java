package ru.graduation_project_topjava.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.TimingExtension;
import ru.graduation_project_topjava.UserTestData;
import ru.graduation_project_topjava.model.User;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class CrudUserRepositoryTest {

    @Autowired
    private CrudUserRepository userRepository;

    @Test
    void findByEmail() {
        User expected = UserTestData.getUser();
        User actual = userRepository.findByEmail(UserTestData.getUser().getEmail().toLowerCase());
        UserTestData.USER_MATCHER.assertMatch(actual, expected);
    }
}