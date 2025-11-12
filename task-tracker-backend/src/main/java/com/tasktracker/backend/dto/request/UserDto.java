package com.tasktracker.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDto(
        @Email
        String email,
        @Size(min = 5, max = 30, message = "min 5 max 30")
        @NotNull(message = "Введите пароль")
        String password) {}
