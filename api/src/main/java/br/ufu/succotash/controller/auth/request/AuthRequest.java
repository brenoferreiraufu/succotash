package br.ufu.succotash.controller.auth.request;

import javax.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank String username, @NotBlank String password) { }
