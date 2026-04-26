package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.exceptions.*;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.JwtCore;
import com.example.sweezcustoms.security.PasswordConfirmation;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.MailService;
import com.example.sweezcustoms.service.PhotoProfileService;
import com.example.sweezcustoms.service.SignatureService;
import com.example.sweezcustoms.utils.InternalizationHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtCore jwtCore;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final MailService mailService;
    private static final String RESET_PREFIX = "mail_confirmation:";
    private final InternalizationHelper internalizationHelper;
    private final SignatureService signatureService;
    private final PhotoProfileService photoProfileService;

    @Override
    @Transactional
    public UserEntity register(UserEntity userEntity, MultipartFile profilePicture, MultipartFile signature) {
        userEntity.setPhotoProfileS3(photoProfileService.uploadPhoto(profilePicture));
        userEntity.setSignatureS3(signatureService.uploadSignature(signature));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return userRepository.save(userEntity);
    }

    @Override
    public AuthenticationToken login(AuthenticationTokenRequest authenticationTokenRequest) {
        UserEntity userEntity = (UserEntity) loadUserByUsername(authenticationTokenRequest.getEmail());
        if(!passwordEncoder.matches(
                authenticationTokenRequest.getPassword(),
                userEntity.getPassword())
        ) throw new IncorrectSubjectOrPassword("Incorrect username or password");
        return new AuthenticationToken(
                jwtCore.generateAccessToken(userEntity),
                jwtCore.generateRefreshToken(userEntity)
        );
    }

    @Override
    public void passwordRecovery(String email) {
        UserEntity userEntity = (UserEntity) loadUserByUsername(email);
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        redisTemplate.opsForValue().set(
                RESET_PREFIX + code,
                userEntity.getMail(),
                15,
                TimeUnit.MINUTES
        );
        Map<String, Object> variables = Map.of("code", code);
        mailService.sendHtmlEmail(
                email,
                internalizationHelper.getTranslation("password.recovery"),
                "password-reset", variables
        );
    }


    @Override
    public void resetPassword(String code, PasswordConfirmation passwordConfirmation) {
        if(!redisTemplate.hasKey(RESET_PREFIX + code)) throw new CodeConfirmationException("");
        if(!passwordConfirmation.getNewPassword().equals(passwordConfirmation.getConfirmPassword()))
            throw new PasswordDoesNotMatchException("");
        String email = redisTemplate.opsForValue().get(RESET_PREFIX + code);
        UserEntity userEntity = (UserEntity) loadUserByUsername(email);
        userEntity.setPassword(passwordEncoder.encode(passwordConfirmation.getNewPassword()));
        userRepository.save(userEntity);
        redisTemplate.delete(RESET_PREFIX + code);
    }

    @Override
    public AuthenticationToken refreshToken(String refreshToken) {
        if(!jwtCore.validationToken(refreshToken))
            throw new AuthenticationException("error.refresh.token.expired");
        String username = jwtCore.extractUsernameFromToken(refreshToken);
        UserDetails userDetails = loadUserByUsername(username);
        return new AuthenticationToken(jwtCore.generateAccessToken(userDetails), refreshToken);
    }

    @Override
    public UserEntity getCurrent() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByPinOrMail(username, username)
                .orElseThrow(() -> new UserNotFoundException("error.user.notfound"));
        log.info("---------------->>>>>>>>>>>>>>>>>>>>>>>>: " + userEntity.getRoles().stream().map(role -> role.getRoleName()).toList().toString());
        return userEntity;
    }
}
