package br.ufu.succotash.security.jwt;

import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    public void loadUserByUsername_shouldReturnUserDetails() {
        String username = "username";
        User user = new User("fullName", "username", "password", Role.CLIENT);
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails actualUserDetails = jwtUserDetailsService.loadUserByUsername(username);

        verify(repository).findByUsername(username);
        assertEquals(user, actualUserDetails);
    }

    @Test
    public void loadUserByUsername_shouldThrowResponseStatusExceptionWhenUserNotFound() {
        String username = "username";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> jwtUserDetailsService.loadUserByUsername(username));

        verify(repository).findByUsername(username);
    }

}