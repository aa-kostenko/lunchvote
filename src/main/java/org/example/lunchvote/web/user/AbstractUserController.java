package org.example.lunchvote.web.user;

import org.example.lunchvote.repository.UserRepository;
import org.example.lunchvote.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.example.lunchvote.HasId;
import org.example.lunchvote.model.User;
import org.example.lunchvote.to.UserTo;
import org.example.lunchvote.util.UserUtil;

import java.util.List;

import static org.example.lunchvote.util.ValidationUtil.assureIdConsistent;
import static org.example.lunchvote.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

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

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        repository.save(UserUtil.createNewFromTo(userTo));
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return repository.findByEmail(email);
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}