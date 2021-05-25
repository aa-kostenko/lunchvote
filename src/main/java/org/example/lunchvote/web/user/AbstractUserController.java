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
import org.springframework.validation.BindException;

import java.util.List;

import static org.example.lunchvote.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.lunchvote.util.validation.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    public List<User> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id " + id));
    }

    public User create(UserTo userTo) {
        log.info("create from to {}", userTo);
        return create(UserUtil.createNewFromTo(userTo));
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.delete(get(id));
    }

    public void update(UserTo userTo) {
        User user = get(userTo.id());
        repository.save(UserUtil.updateFromTo(user, userTo));
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.findByEmail(email);
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
    }
}