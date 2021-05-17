package org.example.lunchvote.repository;

import org.example.lunchvote.model.Role;
import org.example.lunchvote.model.User;
import org.example.lunchvote.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.example.lunchvote.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryTest extends AbstractRepositoryTest{
    @Autowired
    protected UserRepository repository;

    @Test
    void create() {
        User created = repository.save(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.findById(newId).get(), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                repository.save(new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    void delete() {
        repository.delete(USER1_ID);
        assertThrows(NotFoundException.class, () -> repository.findById(USER1_ID).orElseThrow(
                        () -> new NotFoundException("not found User with id " + USER1_ID)));
    }

    @Test
    void get() {
        User user = repository.findById(ADMIN1_ID).get();
        USER_MATCHER.assertMatch(user, admin1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> repository.findById(NOT_FOUND).orElseThrow(
                () -> new NotFoundException("not found User with id " + USER1_ID)));
    }

    @Test
    void getByEmail() {
        User user = repository.findByEmail("admin1@gmail.com");
        USER_MATCHER.assertMatch(user, admin1);
    }

    @Test
    void getAll() {
        List<User> all = repository.findAll();
        USER_MATCHER.assertMatch(all, users);
    }


}
