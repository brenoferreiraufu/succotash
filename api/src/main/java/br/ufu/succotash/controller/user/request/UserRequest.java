package br.ufu.succotash.controller.user.request;

import br.ufu.succotash.model.Role;
import br.ufu.succotash.model.User;
import br.ufu.succotash.validation.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank String fullName,
        @NotBlank @Unique(entity = User.class, field = "username") String username,
        @NotBlank String password,
        @NotNull Role role
) {

    public User toModel() {
        return new User(fullName, username, password, role);
    }
}
