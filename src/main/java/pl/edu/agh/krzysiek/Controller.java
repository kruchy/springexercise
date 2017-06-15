package pl.edu.agh.krzysiek;

import pl.edu.agh.krzysiek.model.Password;
import pl.edu.agh.krzysiek.model.User;
import pl.edu.agh.krzysiek.model.Username;
import pl.edu.agh.krzysiek.service.PasswordValidator;
import pl.edu.agh.krzysiek.service.UserService;
import pl.edu.agh.krzysiek.service.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    UsernameValidator usernameValidator ;

    @Autowired
    PasswordValidator passwordValidator ;

    @PostMapping("/user")
    public Object user(@RequestBody User user, BindingResult bindingResult) {

        String username = user.getUsername();
        String password = user.getPassword();

        validateCredentials(bindingResult, username, password);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            User user1 = userService.saveUser(user);
            return new ResponseEntity<Object>(user1, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping("/username")
    public Object username(@RequestBody Username username, BindingResult bindingResult) {
        usernameValidator.validate(username.getUsername(), bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping("/password")
    public Object password(@RequestBody Password password, BindingResult bindingResult) {
        passwordValidator.validate(password.getPassword(), bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(bindingResult.getAllErrors(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
        }
    }

    private void validateCredentials(BindingResult bindingResult, String username, String password) {
        usernameValidator.validate(username, bindingResult);
        passwordValidator.validate(password, bindingResult);
    }

}
