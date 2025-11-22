package com.tasktracker.backend.controller.rest;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDto> registration(@RequestBody UserDto userDto) {

        JwtAuthenticationDto jwtAuthenticationDto = authService.singIn(userDto);

        return ResponseEntity.ok(jwtAuthenticationDto);
    }
}
