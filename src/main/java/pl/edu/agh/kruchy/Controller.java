package pl.edu.agh.kruchy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.kruchy.model.Password;
import pl.edu.agh.kruchy.model.User;
import pl.edu.agh.kruchy.model.Username;
import pl.edu.agh.kruchy.service.PasswordValidator;
import pl.edu.agh.kruchy.service.UserService;
import pl.edu.agh.kruchy.service.UserValidator;
import pl.edu.agh.kruchy.service.UsernameValidator;

@RestController
public class Controller {

    private final
    UserService userService;

    private final UsernameValidator usernameValidator;

    private final PasswordValidator passwordValidator;

    private final UserValidator userValidator;

    @Autowired
    public Controller(UserService userService, UsernameValidator usernameValidator, PasswordValidator passwordValidator, UserValidator userValidator) {
        this.userService = userService;
        this.usernameValidator = usernameValidator;
        this.passwordValidator = passwordValidator;
        this.userValidator = userValidator;
    }

    @InitBinder("username")
    void initUsernameValidator(WebDataBinder binder) {
        binder.setValidator(usernameValidator);
    }

    @InitBinder("password")
    void initPasswordValidator(WebDataBinder binder) {
        binder.setValidator(passwordValidator);
    }

    @InitBinder("user")
    void initUserValidator(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @PostMapping("/user")
    public Object user(@RequestBody @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            User user1 = userService.saveUser(user);
            return new ResponseEntity<Object>(user1, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping("/username")
    public Object username(@RequestBody @Validated Username username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping("/password")
    public Object password(@RequestBody @Validated Password password, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }
    }

}
