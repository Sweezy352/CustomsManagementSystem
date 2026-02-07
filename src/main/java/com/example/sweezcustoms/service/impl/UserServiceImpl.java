package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.dto.request.UserDtoUpdateRequest;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.exceptions.UserNotFoundException;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity getByPinOrMail(String query) {
        return userRepository.findByPinOrMail(query, query).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity updateUser(UserDtoUpdateRequest userUpdated) {
        UserEntity currentUser = authService.getCurrent();
        currentUser.setMail(userUpdated.getEmail());
        currentUser.setPhone(userUpdated.getPhone());
        return userRepository.save(currentUser);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
