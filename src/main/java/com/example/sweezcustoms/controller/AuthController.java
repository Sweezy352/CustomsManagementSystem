package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.PasswordConfirmation;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.utils.InternalizationHelper;
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
    private final InternalizationHelper internalizationHelper;

    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> register(@Valid @RequestBody UserDtoRequest userDtoRequest) throws BaseException {
        return ResponseEntity.ok(userMapper.toDtoResponse(authService.register(userMapper.toEntity(userDtoRequest))));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(@Valid @RequestBody AuthenticationTokenRequest authenticationTokenRequest) throws BaseException{
        return ResponseEntity.ok(authService.login(authenticationTokenRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationToken> refreshToken(HttpServletRequest request) throws BaseException{
        String refreshToken = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PostMapping("/password-recovery")
    public void resetPassword(@RequestParam String email) throws BaseException{
        authService.passwordRecovery(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String code, @Valid @RequestBody PasswordConfirmation passwordConfirmation) throws BaseException{
        authService.resetPassword(code, passwordConfirmation);
        return ResponseEntity.ok().body(internalizationHelper.getTranslation("password.reset.succeed"));
    }

    @GetMapping("/test-tokens")
    public String testTokens(){
        return "Все работает";
    }
}
