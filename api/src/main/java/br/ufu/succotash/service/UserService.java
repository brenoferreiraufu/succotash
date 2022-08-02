package br.ufu.succotash.service;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String newUser(UserRequest user) {
        return userRepository.save(user.toModel(passwordEncoder)).getId();
    }

    public Optional<User> findUser(String userId) {
        return userRepository.findById(userId);
    }

}
