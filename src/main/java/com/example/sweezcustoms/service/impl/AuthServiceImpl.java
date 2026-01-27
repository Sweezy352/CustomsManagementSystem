package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.exceptions.IncorrectSubjectOrPassword;
import com.example.sweezcustoms.exceptions.UserNotFoundException;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.JwtCore;
import com.example.sweezcustoms.security.PasswordConfirmation;
import com.example.sweezcustoms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtCore jwtCore;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private static final String RESET_PREFIX = "mail_confirmation:";
    private String urlConfirmation;
    private String urlConfirmationPasswordReset;

    @Override
    public UserEntity register(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public AuthenticationToken login(AuthenticationTokenRequest authenticationTokenRequest) {
        UserEntity userEntity = (UserEntity) loadUserByUsername(authenticationTokenRequest.getUsername());
        if(!passwordEncoder.matches(authenticationTokenRequest.getPassword(), userEntity.getPassword())) throw new IncorrectSubjectOrPassword("Incorrect username or password");
        return new AuthenticationToken(jwtCore.generateToken(userEntity), null);
    }

    @Override
    public void loginWithEmail(String email) {
        UserEntity userEntity = userRepository.findByMail(email).orElseThrow(() -> new UserNotFoundException(""));
        String code = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RESET_PREFIX + email, code, 10, TimeUnit.MINUTES);
        String urlConfirmation =
    }

    @Override
    public AuthenticationToken confirmCodeFromEmail(String code) {
        return null;
    }

    @Override
    public void passwordRecovery(String email) {

    }

    @Override
    public AuthenticationToken resetPassword(String code, PasswordConfirmation passwordConfirmation) {
        return null;
    }

    @Override
    public UserEntity getCurrent() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
