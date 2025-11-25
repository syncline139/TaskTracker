package com.tasktracker.backend.controller.rest;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDto> signUp(@RequestBody UserDto userDto) {

        JwtAuthenticationDto jwtAuthenticationDto = authService.singUp(userDto);

        return ResponseEntity.ok(jwtAuthenticationDto);
    }

    @GetMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {

        JwtAuthenticationDto jwtAuthenticationDto = authService.singIn(userDto);

        return ResponseEntity.ok(jwtAuthenticationDto.getAccessToken());
    }

    @PostMapping("/logout")
    public void logout() {

    }
}
