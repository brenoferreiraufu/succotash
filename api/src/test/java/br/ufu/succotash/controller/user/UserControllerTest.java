package br.ufu.succotash.controller.user;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.controller.user.response.UserResponse;
import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void newUser_shouldReturnOkWithLocation() {
        UserRequest user = new UserRequest("Pedro", "usuario1", "123", Role.WORKER);
        when(userService.newUser(user)).thenReturn("123");

        ResponseEntity<?> response = userController.newUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(URI.create("/api/v1/user/123"), response.getHeaders().getLocation());
    }

    @Test
    public void findUser_shouldReturnUser() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        when(userService.findUser("123")).thenReturn(Optional.of(user));

        ResponseEntity<UserResponse> response = userController.findUser("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new UserResponse(user.getFullName(), user.getUsername(), user.getRole()), response.getBody());
    }

    @Test
    public void findUser_shouldReturnNotFound() {
        when(userService.findUser("123")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userController.findUser("123"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
