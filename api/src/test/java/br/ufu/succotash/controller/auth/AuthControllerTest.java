package br.ufu.succotash.controller.auth;

import br.ufu.succotash.controller.auth.request.AuthRequest;
import br.ufu.succotash.controller.auth.response.AuthResponse;
import br.ufu.succotash.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void generateAuthenticationToken_shouldReturnOkWithToken() {
        AuthRequest authRequest = new AuthRequest("test", "password");
        String token = "token";
        when(authService.authenticate(authRequest.username(), authRequest.password())).thenReturn(token);

        ResponseEntity<?> response = authenticationController.generateAuthenticationToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new AuthResponse(token), response.getBody());
    }
}
