package pl.edu.agh.krzysiek.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Service("passwordValidator")
@Component
public class PasswordValidator implements Validator {

    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String password = (String) o;


        if (!password.matches(PASSWORD_REGEX)) {
            errors.rejectValue("password", "password.invalid");
        }

    }
}
