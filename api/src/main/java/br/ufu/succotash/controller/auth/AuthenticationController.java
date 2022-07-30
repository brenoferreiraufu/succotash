package br.ufu.succotash.controller.auth;

import br.ufu.succotash.controller.auth.request.AuthRequest;
import br.ufu.succotash.controller.auth.response.AuthResponse;
import br.ufu.succotash.security.jwt.JwtUserDetailsService;
import br.ufu.succotash.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private JwtUtil jwtUtil;
    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody AuthRequest authRequest) {

        authenticate(authRequest.username(), authRequest.password());

        var userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.username());
        var token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
