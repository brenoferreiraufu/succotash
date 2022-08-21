package br.ufu.succotash.controller.auth;

import br.ufu.succotash.controller.auth.request.AuthRequest;
import br.ufu.succotash.controller.auth.response.AuthResponse;
import br.ufu.succotash.security.jwt.JwtUserDetailsService;
import br.ufu.succotash.security.jwt.JwtUtil;
import br.ufu.succotash.service.AuthService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired private AuthService authService;

    @PostMapping
    public ResponseEntity<?> generateAuthenticationToken(@Valid @RequestBody AuthRequest authRequest) {
        var token = authService.authenticate(authRequest.username(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
