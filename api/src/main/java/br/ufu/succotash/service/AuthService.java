package br.ufu.succotash.service;

import br.ufu.succotash.security.jwt.JwtUserDetailsService;
import br.ufu.succotash.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private AuthenticationManager authenticationManager;

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var userDetails = jwtUserDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }
}
