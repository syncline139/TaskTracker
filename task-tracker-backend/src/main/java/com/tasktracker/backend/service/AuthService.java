package com.tasktracker.backend.service;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.dto.request.UserDto;

public interface AuthService {
    JwtAuthenticationDto singUp(UserDto userDto);
    JwtAuthenticationDto singIn(UserDto userDto);

}
