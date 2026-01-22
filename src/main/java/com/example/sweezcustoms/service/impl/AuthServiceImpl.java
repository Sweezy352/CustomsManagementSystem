package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.exceptions.IncorrectSubjectOrPassword;
import com.example.sweezcustoms.exceptions.UserNotFoundException;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.JwtCore;
import com.example.sweezcustoms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtCore jwtCore;
    private final PasswordEncoder passwordEncoder;

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

    }

    @Override
    public AuthenticationToken confirmCodeFromEmail(String code) {
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
