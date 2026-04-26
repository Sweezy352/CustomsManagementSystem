package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoUpdateRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Пользователи", description = "Получение и обновление данных пользователей")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(
            summary = "Получить пользователя по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(
            @Parameter(description = "ID пользователя") @PathVariable Long id
    ) {
        return ResponseEntity.ok(userMapper.toDtoResponse(userService.getById(id)));
    }

    @Operation(
            summary = "Получить пользователя по email или ПИН",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @GetMapping("/get-by-email")
    public ResponseEntity<UserDtoView> getUserByEmail(
            @Parameter(description = "Email или ПИН пользователя") @RequestParam String email
    ) {
        return ResponseEntity.ok(userMapper.toDtoView(userService.getByPinOrMail(email)));
    }

    @Operation(
            summary = "Обновить данные пользователя",
            description = "Обновляет email и номер телефона текущего пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные обновлены"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @PutMapping("/update-user-info")
    public ResponseEntity<UserDtoResponse> updateUserInfo(@RequestBody UserDtoUpdateRequest userDtoUpdateRequest) {
        return ResponseEntity.ok(userMapper.toDtoResponse(userService.updateUser(userDtoUpdateRequest)));
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Доступно только администраторам.",
            responses = @ApiResponse(responseCode = "200", description = "Список пользователей")
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDtoView>> getAllUsers() {
        return ResponseEntity.ok(userMapper.toDtoViewList(userService.getAllUsers()));
    }
}
