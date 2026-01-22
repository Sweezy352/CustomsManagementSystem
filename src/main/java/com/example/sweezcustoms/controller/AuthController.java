package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> register(@RequestBody UserDtoRequest userDtoRequest) throws BaseException {
        return ResponseEntity.ok(userMapper.toDtoResponse(authService.register(userMapper.toEntity(userDtoRequest))));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@RequestBody AuthenticationTokenRequest authenticationTokenRequest) throws BaseException{
        return ResponseEntity.ok(authService.login(authenticationTokenRequest));
    }
}
