package com.example.sweezcustoms.service;

import com.example.sweezcustoms.dto.request.UserDtoUpdateRequest;
import com.example.sweezcustoms.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getById(Long id);
    UserEntity getByPinOrMail(String query);
    UserEntity updateUser(UserDtoUpdateRequest userUpdated);
    List<UserEntity> getAllUsers();

}