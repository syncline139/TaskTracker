package com.tasktracker.backend.service.impl;

import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.entity.User;

public interface AuthService {
    void createdUser(UserDto user);
}
