package com.tasktracker.backend.service.impl;

import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.entity.User;
import com.tasktracker.backend.mapper.UserMapper;
import com.tasktracker.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public void createdUser(UserDto userDto) {
        userRepository.save(userMapper.convertUserDtoToUser(userDto));
    }
}
