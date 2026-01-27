package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.PasswordConfirmation;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserEntity register(UserEntity userEntity);
    AuthenticationToken login(AuthenticationTokenRequest authenticationTokenRequest);
    void loginWithEmail(String email);
    AuthenticationToken confirmCodeFromEmail(String code);
    void passwordRecovery(String email);
    AuthenticationToken resetPassword(String code, PasswordConfirmation passwordConfirmation);
    UserEntity getCurrent();
}
