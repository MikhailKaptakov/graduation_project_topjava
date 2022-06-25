package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Role;
import ru.graduation_project_topjava.model.User;
import ru.graduation_project_topjava.model.Vote;

public class UserTestData {
    public static final MatcherFactory.Matcher<Vote> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class);

    public static final long USER_ID = 100;
    public static final long ADMIN_ID = 101;

    public static final User user = new User("User","userMail@test.test", "userPassword", Role.USER);
    public static final User admin = new User("Admin","adminMail@test.test", "adminPassword",
            Role.ADMIN, Role.USER);

    static {
        user.setId(USER_ID);
        admin.setId(ADMIN_ID);
    }
}
