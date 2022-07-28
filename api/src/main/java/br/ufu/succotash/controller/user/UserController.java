package br.ufu.succotash.controller.user;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.controller.user.response.UserResponse;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Transactional
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> newUser(@Valid @RequestBody UserRequest user) {
        String userId = userService.newUser(user);
        URI location = URI.create("/api/v1/users/".concat(userId));
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userId) {
        Optional<User> retrievedUser = userService.findUser(userId);
        return retrievedUser.map(UserResponse::toResponseEntity).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
