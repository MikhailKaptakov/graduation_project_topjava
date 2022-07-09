package ru.graduation_project_topjava.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.repository.CrudUserRepository;
import ru.graduation_project_topjava.util.UserUtil;
import ru.graduation_project_topjava.util.validation.ValidationUtil;
import ru.graduation_project_topjava.web.AuthorizedUser;

@Service
public class UserService implements UserDetailsService {

    private final CrudUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(CrudUserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    public User getUser(long id) {
        return ValidationUtil.checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }
}
