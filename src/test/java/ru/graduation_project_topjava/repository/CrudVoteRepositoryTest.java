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
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(TimingExtension.class)
class CrudVoteRepositoryTest {

    @Autowired
    private CrudVoteRepository voteRepository;

    @Test
    void findAllByDate() {
        List<Vote> actual = voteRepository.findAllByDate(LocalDate.now());
        List<Vote> expected = VoteTestData.getActualRestaurant2Votes();
        VoteTestData.VOTE_MATCHER.assertMatch(actual, expected);
        checkVotesIdFields(actual, expected);
    }

    private void checkVotesIdFields(List<Vote> actualVotes, List<Vote> expectedVotes) {
        for (int i = 0; i < actualVotes.size(); i++) {
            if (!actualVotes.get(i).getRestaurantId().equals(expectedVotes.get(i).getRestaurantId())) {
                throw new NotEqualException();
            }
            if (!actualVotes.get(i).getUserId().equals(expectedVotes.get(i).getUserId())) {
                throw new NotEqualException();
            }
        }
    }

    @Test
    void getActualVote() {
        Vote vote = voteRepository.getVote(UserTestData.USER_ID, LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(vote,VoteTestData.getActualVote1UserActualRestaurant());
    }

    @Test
    void getNullVote() {
        Vote vote = voteRepository.getVote(UserTestData.USER2_ID, LocalDate.now()).orElse(null);
        VoteTestData.VOTE_MATCHER.assertMatch(vote,null);
    }
}