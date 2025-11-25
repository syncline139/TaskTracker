package com.tasktracker.backend.controller.rest;

import com.tasktracker.backend.dto.request.UserInfoDto;
import com.tasktracker.backend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<UserInfoDto> userInfo (@AuthenticationPrincipal UserDetails userDetails) {

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        UserInfoDto userInfoDto = new UserInfoDto(
                customUserDetails.user().getId(),
                customUserDetails.user().getEmail());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfoDto);
    }
}
