package ru.graduation_project_topjava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudUserRepository;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.util.exception.ConditionFailedException;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class UserService {

    private static final LocalTime maxRevoteTime = LocalTime.of(11,00);

    private final CrudVoteRepository voteRepository;

    private final CrudUserRepository userRepository;

    public UserService(CrudVoteRepository voteRepository, CrudUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addVote(User user, Restaurant restaurant) {
        Vote vote = voteRepository.getVote(user.getId(), LocalDate.now()).orElse(null);
        if (vote != null) {
            if (LocalTime.now().isAfter(maxRevoteTime)) {
                throw new ConditionFailedException("Time over 11 hours. You can't revote");
            }
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
            return;
        }
        vote = new Vote(user, restaurant);
        voteRepository.save(vote);
    }

}
