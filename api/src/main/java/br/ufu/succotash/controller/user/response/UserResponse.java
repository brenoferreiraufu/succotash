package br.ufu.succotash.controller.user.response;

import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import org.springframework.http.ResponseEntity;

public record UserResponse(String fullName, String username, Role role) {

    public static ResponseEntity<UserResponse> toResponseEntity(User user) {
        return ResponseEntity.ok(new UserResponse(user.getFullName(), user.getUsername(), user.getRole()));
    }
}
