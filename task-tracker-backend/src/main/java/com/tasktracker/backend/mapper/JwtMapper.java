package com.tasktracker.backend.mapper;

import com.tasktracker.backend.dto.request.JwtAuthenticationDto;
import com.tasktracker.backend.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtMapper {

    private final ModelMapper mapper;

    public RefreshToken convertJwtAuthenticationDtoToRefreshToken(
            JwtAuthenticationDto jwtAuthenticationDto
    ){
      return  mapper.map(jwtAuthenticationDto, RefreshToken.class);
    }
}
