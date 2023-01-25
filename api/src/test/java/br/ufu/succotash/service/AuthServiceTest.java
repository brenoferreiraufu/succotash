package br.ufu.succotash.service;

import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.security.jwt.JwtUserDetailsService;
import br.ufu.succotash.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    public void authenticate_shouldReturnToken() {
        String username = "test";
        String password = "password";
        UserDetails userDetails = new User("test", "password", "1", Role.WORKER);
        String token = "token";
        when(jwtUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        String result = authService.authenticate(username, password);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(jwtUserDetailsService).loadUserByUsername(username);
        verify(jwtUtil).generateToken(userDetails);
        assertEquals(token, result);
    }

}
