package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Role;
import ru.graduation_project_topjava.model.User;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "email", "password");

    public static final long USER_ID = 100;
    public static final long ADMIN_ID = 101;
    public static final long USER2_ID = 102;

    private static final User user = new User("User","userMail@test.test", "userPassword", Role.USER);
    private static final User admin = new User("Admin","adminMail@test.test", "adminPassword",
            Role.ADMIN, Role.USER);
    private static final User user2 = new User("User2","user2Mail@test.test", "user2Password", Role.USER);

    static {
        user.setId(USER_ID);
        admin.setId(ADMIN_ID);
        user2.setId(USER2_ID);
    }

    public static User getAdmin() {
        return new User(admin);
    }

    public static User getUser() {
        return new User(user);
    }

    public static User getUser2() {
        return new User(user2);
    }
}
