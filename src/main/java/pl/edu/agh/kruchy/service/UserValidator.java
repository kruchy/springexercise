package pl.edu.agh.kruchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.agh.kruchy.model.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UsernameValidator usernameValidator;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        passwordValidator.validate(user.getPassword(), errors);
        usernameValidator.validate(user.getUsername(), errors);
    }
}
