package pl.edu.agh.kruchy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.agh.kruchy.model.Password;
import pl.edu.agh.kruchy.model.User;
import pl.edu.agh.kruchy.model.Username;
import pl.edu.agh.kruchy.service.PasswordValidator;
import pl.edu.agh.kruchy.service.UserService;
import pl.edu.agh.kruchy.service.UserValidator;
import pl.edu.agh.kruchy.service.UsernameValidator;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.edu.agh.kruchy.ControllerTest.UserBuilder.anUser;


//TODO Rewrite tests
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UsernameValidator usernameValidator;

    @MockBean
    private PasswordValidator passwordValidator;

    @MockBean
    private UserValidator userValidator;

    @Test
    public void saveValidUserReturnsOk() throws Exception {
        doReturn(null).when(userService).saveUser(any(User.class));
        doReturn(Optional.empty()).when(userService).findUser(any(String.class));

        this.mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\",\"password\":\"pasS.1asd\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //    @Test
    public void saveInvalidUserReturnsInternalServerError() throws Exception {
        User mock = mock(User.class);
        doReturn(mock).when(userService).saveUser(any(User.class));
        doReturn(Optional.empty()).when(userService).findUser(any(String.class));

        this.mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"username\",\"password\":\"passworD\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    //    @Test
    public void failToSaveUserWhenUsernameIsInvalidReturnsInternalServerError() throws Exception {
        User mock = mock(User.class);
        doReturn(mock).when(userService).saveUser(any(User.class));
        doReturn(Optional.empty()).when(userService).findUser(any(String.class));

        this.mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"us\",\"password\":\"pasS.1asd\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    //    @Test
    public void checkInvalidUsernameReturnsInternalServerError() throws Exception {
        User mock = mock(User.class);
        doReturn(mock).when(userService).saveUser(any(User.class));
        doReturn(Optional.empty()).when(userService).findUser(any(String.class));

        this.mvc.perform(post("/username")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"us\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    //    @Test
    public void checkExistingUsernameReturnsInternalServerError() throws Exception {
        User mock = anUser().withUsername("aaaaa").withPassword("password").build();
        doReturn(Optional.of(mock)).when(userService).findUser(any(String.class));

        doReturn(mock).when(userService).saveUser(any(User.class));
        this.mvc.perform(post("/username")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"aaaaa\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    static class UserBuilder {
        private String username;
        private String password;

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public User build() {
            return new User(new Username(username), new Password(password));
        }
    }


    //    @Test
    public void checkInvalidPasswordReturnsInternalServerError() throws Exception {
        this.mvc.perform(post("/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"password\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void checkValidPasswordReturnsOK() throws Exception {
        this.mvc.perform(post("/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"pAssword1\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}