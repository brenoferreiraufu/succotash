package br.ufu.succotash.controller.user;

import br.ufu.succotash.controller.user.request.UserRequest;
import br.ufu.succotash.controller.user.response.UserResponse;
import br.ufu.succotash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@Transactional
public class UserController {

    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity<?> newUser(@Valid @RequestBody UserRequest user) {
        var userId = userService.newUser(user);
        var location = URI.create("/api/v1/users/".concat(userId));
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userId) {
        var retrievedUser = userService.findUser(userId);
        return retrievedUser.map(UserResponse::toResponseEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
