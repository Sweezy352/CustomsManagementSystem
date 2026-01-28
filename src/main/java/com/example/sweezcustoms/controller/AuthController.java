package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> register(@Valid @RequestBody UserDtoRequest userDtoRequest) throws BaseException {
        return ResponseEntity.ok(userMapper.toDtoResponse(authService.register(userMapper.toEntity(userDtoRequest))));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@Valid @RequestBody AuthenticationTokenRequest authenticationTokenRequest) throws BaseException{
        return ResponseEntity.ok(authService.login(authenticationTokenRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationToken> refreshToken(HttpServletRequest request){
        String refreshToken = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/test-tokens")
    public String testTokens(){
        return "Все работает";
    }
}
