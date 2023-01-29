package br.ufu.succotash.service;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void newUser_shouldCreateNewUser() {
        UserRequest userRequest = new UserRequest("fullName", "username", "password", Role.WORKER);
        User expectedUser = new User("fullName", "username", "encodedPassword", Role.WORKER);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(userRequest.toModel(passwordEncoder))).thenReturn(expectedUser);

        String userId = userService.newUser(userRequest);

        verify(userRepository).save(userRequest.toModel(passwordEncoder));
        assertEquals(expectedUser.getId(), userId);
    }

    @Test
    public void findUser_shouldReturnUser() {
        String userId = "userId";
        User expectedUser = new User("fullName", "username", "encodedPassword", Role.WORKER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        Optional<User> actualUser = userService.findUser(userId);

        verify(userRepository).findById(userId);
        assertEquals(Optional.of(expectedUser), actualUser);
    }

    @Test
    public void findUser_shouldReturnEmptyWhenUserNotFound() {
        String userId = "userId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> actualUser = userService.findUser(userId);

        verify(userRepository).findById(userId);
        assertEquals(Optional.empty(), actualUser);
    }

}
