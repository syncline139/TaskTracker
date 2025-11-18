package com.tasktracker.backend.service.impl;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.entity.RefreshToken;
import com.tasktracker.backend.entity.User;
import com.tasktracker.backend.mapper.JwtMapper;
import com.tasktracker.backend.mapper.UserMapper;
import com.tasktracker.backend.repository.JwtRepository;
import com.tasktracker.backend.repository.UserRepository;
import com.tasktracker.backend.security.jwt.JwtService;
import com.tasktracker.backend.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final JwtMapper jwtMapper;
    private final JwtRepository jwtRepository;


    @Override
    @Transactional
    public JwtAuthenticationDto  singIn(UserDto userDto) {
        User user = userMapper.convertUserDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        JwtAuthenticationDto jwtDto = jwtService.generatedAuthToken(user.getEmail());
        RefreshToken refreshToken = jwtMapper.convertJwtAuthenticationDtoToRefreshToken(jwtDto);
        refreshToken.setUser(user);

        userRepository.save(user);
        jwtRepository.save(refreshToken);

        return jwtDto;
    }

}
