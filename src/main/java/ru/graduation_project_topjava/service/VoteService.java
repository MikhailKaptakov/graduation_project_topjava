package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudUserRepository;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

@Service
public class VoteService {
    private final CrudVoteRepository voteRepository;

    public VoteService(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote getVote(long voteId, long userId) {
        Vote vote = ValidationUtil.checkNotFoundWithId(voteRepository.findById(voteId).orElse(null), voteId);
        //todo dataJpaVoteRepository and check owner аналогично тому как это сделано в мил репозитории топ джавы
        return vote;
    }
}
