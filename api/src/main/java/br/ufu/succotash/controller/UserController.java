package br.ufu.succotash.controller;

import br.ufu.succotash.controller.request.UserRequest;
import br.ufu.succotash.controller.response.UserResponse;
import br.ufu.succotash.model.User;
import br.ufu.succotash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Object> newUser(@Valid @RequestBody UserRequest user) {
        var userId = userRepository.save(user.toModel()).getId();
        return ResponseEntity.ok().location(URI.create("/api/v1/users/".concat(userId))).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userId) {
        var retrievedUser = userRepository.findById(userId);
        return retrievedUser.map(UserResponse::toResponseEntity).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
