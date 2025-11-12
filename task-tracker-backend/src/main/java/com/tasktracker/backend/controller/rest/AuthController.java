package com.tasktracker.backend.controller.rest;

import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<String> registration(@RequestBody UserDto userDto) {

        authService.createdUser(userDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Вы успешно зарегистрировались: %s".formatted(userDto.email()));
    }
}
