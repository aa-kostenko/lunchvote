package org.example.lunchvote.web.user;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.example.lunchvote.HasIdAndEmail;
import org.example.lunchvote.model.User;
import org.example.lunchvote.repository.UserRepository;
import org.example.lunchvote.web.ExceptionInfoHandler;


@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserRepository repository;

    public UniqueMailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        if (StringUtils.hasText(user.getEmail())) {
            User dbUser = repository.findByEmail(user.getEmail().toLowerCase());
            if (dbUser != null && !dbUser.getId().equals(user.getId())) {
                errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
