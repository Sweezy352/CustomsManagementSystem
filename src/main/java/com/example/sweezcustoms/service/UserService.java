package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getById(Long id);
    UserEntity getByUsernameOrMail(String query);
    UserEntity updateUser(UserEntity userEntity);
    List<UserEntity> getAllUsers();

}