package ru.graduation_project_topjava;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.web.AuthorizedUser;

import java.util.Locale;

public class TestUtil {

    public static void mockAuthorize(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new AuthorizedUser(user), null, user.getRoles()));
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors
                .authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }
}
