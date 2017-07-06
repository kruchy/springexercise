package pl.edu.agh.kruchy.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.agh.kruchy.model.Password;

@Component
public class PasswordValidator implements Validator {

    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return Password.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Password password = (Password) o;

        if (!password.getPassword().matches(PASSWORD_REGEX)) {
            errors.rejectValue("password", "password.invalid");
        }

    }
}
