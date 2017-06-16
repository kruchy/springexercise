package pl.edu.agh.kruchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UsernameValidator implements Validator {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{5,}$";

    @Autowired
    private UserService userService;
    Pattern pattern = Pattern.compile(USERNAME_REGEX);


    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String username = (String) o;
        userService.findUser(username).ifPresent(user ->
                errors.rejectValue("username", "username.exists"));

        if (!pattern.matcher(username).find()) {
            errors.rejectValue("username", "username.invalid");
        }
    }
}
