package org.example.lunchvote;

import org.example.lunchvote.model.Role;
import org.example.lunchvote.model.User;
import org.example.lunchvote.web.json.JsonUtil;

import java.util.Collections;
import java.util.List;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int ADMIN1_ID = 1;
    public static final int ADMIN2_ID = ADMIN1_ID + 1;
    public static final int USER1_ID = ADMIN2_ID + 1;
    public static final int USER2_ID = USER1_ID + 1;
    public static final int USER3_ID = USER2_ID + 1;

    public static final int NOT_FOUND = 0;

    public static final User admin1 = new User(ADMIN1_ID, "Admin1", "admin1@gmail.com", "admin1", Role.ADMIN);
    public static final User admin2 = new User(ADMIN2_ID, "Admin2", "admin2@gmail.com", "admin2", Role.ADMIN);
    public static final User user1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password2", Role.USER);
    public static final User user3 = new User(USER3_ID, "User3", "user3@yandex.ru", "password3", Role.USER);
    public static final List<User> users = List.of(admin1, admin2, user1, user2, user3);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user1);
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
