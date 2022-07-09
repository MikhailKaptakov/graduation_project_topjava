package ru.graduation_project_topjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudUserRepository;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.repository.DataJpaRestaurantRepository;
import ru.graduation_project_topjava.util.exception.ConditionFailedException;
import ru.graduation_project_topjava.web.AuthorizedUser;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class UserService implements UserDetailsService {

    public static final LocalTime MAX_REVOTE_TIME = LocalTime.of(11,00);
    //todo придумать способ тестировать с заменой времени
    private final CrudVoteRepository voteRepository;

    private final CrudUserRepository userRepository;

    private final DataJpaRestaurantRepository restaurantRepository;

    public UserService(CrudVoteRepository voteRepository, CrudUserRepository userRepository,
                       DataJpaRestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Vote addVote(Long userId, Long restaurantId) {
        Vote vote = voteRepository.getVote(userId, LocalDate.now()).orElse(null);
        if (vote != null) {
            if (LocalTime.now().isAfter(MAX_REVOTE_TIME)) {
                throw new ConditionFailedException("Time over 11 hours. You can't revote");
            }
            Restaurant restaurant = restaurantRepository.findById(restaurantId);
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Restaurant restaurant = restaurantRepository.findById(restaurantId);
        vote = new Vote(user, restaurant);
        return voteRepository.save(vote);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
