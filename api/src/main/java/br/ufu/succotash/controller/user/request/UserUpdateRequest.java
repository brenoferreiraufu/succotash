package br.ufu.succotash.controller.user.request;

import br.ufu.succotash.model.Role;
import br.ufu.succotash.model.User;

public record UserUpdateRequest(
        String fullName,
        String password,
        Role role
) {

    public User updateModel(User user) {
        user.setRole(role);
        user.setFullName(fullName);
        user.setPassword(password);
        return user;
    }
}
