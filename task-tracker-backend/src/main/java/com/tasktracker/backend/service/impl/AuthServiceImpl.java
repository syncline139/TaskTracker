package com.tasktracker.backend.service.impl;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.dto.request.UserDto;
import com.tasktracker.backend.entity.RefreshToken;
import com.tasktracker.backend.entity.User;
import com.tasktracker.backend.exception.сustom.RefreshTokenNotFoundException;
import com.tasktracker.backend.mapper.JwtMapper;
import com.tasktracker.backend.mapper.UserMapper;
import com.tasktracker.backend.properties.JwtProperties;
import com.tasktracker.backend.repository.JwtRepository;
import com.tasktracker.backend.repository.UserRepository;
import com.tasktracker.backend.security.jwt.JwtService;
import com.tasktracker.backend.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private final JwtProperties jwtProperties;


    @Override
    @Transactional
    public JwtAuthenticationDto singUp(UserDto userDto) {
        User user = userMapper.convertUserDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        JwtAuthenticationDto jwtDto = jwtService.generatedAuthToken(user.getEmail());
        RefreshToken refreshToken = jwtMapper.convertJwtAuthenticationDtoToRefreshToken(jwtDto);
        refreshToken.setUser(user);

        userRepository.save(user);
        jwtRepository.save(refreshToken);

        return jwtDto;
    }

    @Override
    @Transactional
    // todo хз работает эта зулапа или нет проверить, я не проверял мне пох
    public JwtAuthenticationDto singIn(UserDto userDto) { // 2025-11-22 19:23:55.335037

        User user = loadUserByCredentials(userDto.getEmail(), userDto.getPassword());

        RefreshToken refreshToken = jwtRepository.findRefreshTokenByEmail(userDto.getEmail())
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (refreshToken.getExpiresAt().toLocalDateTime().isBefore(LocalDateTime.now())) {

            JwtAuthenticationDto jwtAuthenticationDto = jwtService.generatedAuthToken(user.getEmail());

            long expiryTime = System.currentTimeMillis() + jwtProperties.getRefreshTokenLifeTime();

            refreshToken.setExpiresAt(new Timestamp(expiryTime));
            refreshToken.setRefreshToken(jwtAuthenticationDto.getRefreshToken());
            jwtRepository.flush();

        }
        return jwtService.refreshAccessToken(userDto.getEmail(), refreshToken.getRefreshToken());
    }

    private User loadUserByCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователя с почтой: %s не существует".formatted(email)
                ));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(
                    "Неверный пароль для пользователя с почтой: %s".formatted(email)
            );
        }

        return user;
    }

}
