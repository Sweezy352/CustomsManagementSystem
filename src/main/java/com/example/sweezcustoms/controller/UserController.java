package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoUpdateRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.toDtoResponse(userService.getById(id)));
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<UserDtoView> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userMapper.toDtoView(userService.getByUsernameOrMail(email)));
    }

    @PutMapping("/update-user-info")
    public ResponseEntity<UserDtoResponse> updateUserInfo(@RequestBody UserDtoUpdateRequest userDtoUpdateRequest){
        return ResponseEntity.ok(userMapper.toDtoResponse(userService.updateUser(userDtoUpdateRequest)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDtoView>> getAllUsers(){
        return ResponseEntity.ok(userMapper.toDtoViewList(userService.getAllUsers()));
    }
}
