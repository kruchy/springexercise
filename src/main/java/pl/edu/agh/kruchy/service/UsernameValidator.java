package pl.edu.agh.kruchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.agh.kruchy.model.Username;

import java.util.regex.Pattern;

@Component
public class UsernameValidator implements Validator {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{5,}$";

    private final UserService userService;
    private Pattern pattern = Pattern.compile(USERNAME_REGEX);

    @Autowired
    public UsernameValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Username.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Username username = (Username) o;
        userService.findUser(username.getUsername()).ifPresent(user ->
                errors.rejectValue("username", "username.exists"));

        if (!pattern.matcher(username.getUsername()).find()) {
            errors.rejectValue("username", "username.invalid");
        }
    }
}
