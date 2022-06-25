package ru.graduation_project_topjava.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.graduation_project_topjava.*;
import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class CrudVoteRepositoryTest {

    @Autowired
    private CrudVoteRepository voteRepository;

    @Test
    void findAllByDate() {
        List<Vote> votes = voteRepository.findAllByDate(LocalDate.now());
        VoteTestData.VOTE_MATCHER.assertMatch(votes, VoteTestData.actualRestaurant2Votes);
        for (int i = 0; i < votes.size(); i++) {
            if (!votes.get(i).getRestaurantId().equals(VoteTestData.actualRestaurant2Votes.get(i).getRestaurantId())) {
                throw new NotEqualException();
            }
            if (!votes.get(i).getUserId().equals(VoteTestData.actualRestaurant2Votes.get(i).getUserId())) {
                throw new NotEqualException();
            }
        }
    }

    @Test
    void getActualVote() {
        Vote vote = voteRepository.getVote(UserTestData.USER_ID, LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(vote,VoteTestData.actualVote1UserActualRestaurant);
    }

    @Test
    void getNullVote() {
        Vote vote = voteRepository.getVote(UserTestData.USER2_ID, LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(vote,null);
    }
}