package org.example.lunchvote.web.user;

import org.example.lunchvote.HasId;
import org.example.lunchvote.model.User;
import org.example.lunchvote.repository.UserRepository;
import org.example.lunchvote.to.UserTo;
import org.example.lunchvote.util.UserUtil;
import org.example.lunchvote.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;

import java.util.List;

import static org.example.lunchvote.util.UserUtil.*;
import static org.example.lunchvote.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.lunchvote.util.validation.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    @Autowired
    protected UserRepository repository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return repository.findAll();
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id " + id));
    }

    public User create(UserTo userTo) {
        return create(createNewFromTo(userTo));
    }

    public User create(User user) {
        checkNew(user);
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    public void delete(int id) {
        repository.delete(get(id));
    }

    public void update(UserTo userTo) {
        User user = get(userTo.id());
        repository.save(prepareToSave(updateFromTo(user, userTo), passwordEncoder));
    }

    public User getByMail(String email) {
        return repository.findByEmail(email);
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
    }
}