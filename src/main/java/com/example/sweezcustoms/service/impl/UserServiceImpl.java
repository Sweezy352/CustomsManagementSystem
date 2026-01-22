package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.exceptions.UserNotFoundException;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity getByUsernameOrMail(String query) {
        return userRepository.findByUsernameOrMail(query, query).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
