package br.ufu.succotash.controller.user;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.controller.user.request.UserUpdateRequest;
import br.ufu.succotash.controller.user.response.UserResponse;
import br.ufu.succotash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@Transactional
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Object> newUser(@Valid @RequestBody UserRequest user) {
        var userId = userRepository.save(user.toModel(passwordEncoder)).getId();
        var location = URI.create("/api/v1/users/".concat(userId));
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userId) {
        var retrievedUser = userRepository.findById(userId);
        return retrievedUser.map(UserResponse::toResponseEntity).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> editUser(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest userUpdate) {
        var retrievedUser = userRepository.findById(userId);

        if (retrievedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var user = userUpdate.updateModel(retrievedUser.get());
        userRepository.save(user);

        var location = URI.create("/api/v1/users/".concat(userId));
        return ResponseEntity.ok().location(location).build();
    }
}
