package br.ufu.succotash.controller.user.request;

import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.domain.validation.Unique;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank
        String fullName,
        @NotBlank
        @Unique(entity = User.class, field = "username")
        String username,
        @NotBlank
        String password,
        @NotNull
        Role role
) {

    public User toModel(PasswordEncoder passwordEncoder) {
        return new User(fullName, username, passwordEncoder.encode(password), role);
    }
}
