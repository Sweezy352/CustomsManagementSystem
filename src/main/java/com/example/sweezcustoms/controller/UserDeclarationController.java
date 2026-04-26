package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.UserDeclarationDtoResponse;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.UserDeclarationMapper;
import com.example.sweezcustoms.service.UserDeclarationService;
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

@Tag(name = "Декларации физических лиц", description = "Создание и получение таможенных деклараций физических лиц")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/user-declarations")
@RequiredArgsConstructor
public class UserDeclarationController {
    private final UserDeclarationService userDeclarationService;
    private final UserDeclarationMapper userDeclarationMapper;

    @Operation(
            summary = "Создать декларацию",
            description = "Создаёт новую таможенную декларацию для физического лица.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация успешно создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<UserDeclarationDtoResponse> createDeclaration(
            @RequestBody UserDeclarationDtoRequest userDeclarationDtoRequest
    ) throws BaseException {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponse(
                        userDeclarationService.createDeclaration(
                                userDeclarationMapper.toEntity(userDeclarationDtoRequest)
                        )
                )
        );
    }

    @Operation(
            summary = "Получить все декларации",
            description = "Возвращает все декларации в системе (доступно администраторам и инспекторам).",
            responses = @ApiResponse(responseCode = "200", description = "Список деклараций")
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDeclarationDtoResponse>> getAllDeclarations() throws BaseException {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponseList(
                        userDeclarationService.getAllDeclarations()
                )
        );
    }

    @Operation(
            summary = "Получить все декларации пользователя",
            description = "Возвращает полный список деклараций для указанного пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список деклараций"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @GetMapping("/get-all-full/{userId}")
    public ResponseEntity<List<UserDeclarationDtoResponse>> getAllUserDeclarations(
            @Parameter(description = "ID пользователя") @PathVariable Long userId
    ) throws BaseException {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponseList(
                        userDeclarationService.getFullUserDeclarations(userId)
                )
        );
    }

    @Operation(
            summary = "Получить декларацию по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация найдена"),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserDeclarationDtoResponse> getDeclarationById(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) throws BaseException {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponse(
                        userDeclarationService.getDeclarationById(id)
                )
        );
    }

    @Operation(
            summary = "Подать декларацию на проверку",
            description = "Меняет статус DRAFT → SUBMITTED и автоматически генерирует платёжный счёт на основе налогов по товарам.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация подана"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе DRAFT", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @PatchMapping("/submit/{id}")
    public ResponseEntity<UserDeclarationDtoResponse> submitDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponse(
                        userDeclarationService.submitDeclaration(id)
                )
        );
    }

    @Operation(
            summary = "Одобрить декларацию",
            description = "Инспектор меняет статус SUBMITTED → APPROVED. Доступно только INSPECTOR и ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация одобрена"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе SUBMITTED", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @PatchMapping("/approve/{id}")
    public ResponseEntity<UserDeclarationDtoResponse> approveDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponse(
                        userDeclarationService.approveDeclaration(id)
                )
        );
    }

    @Operation(
            summary = "Отклонить декларацию",
            description = "Инспектор меняет статус SUBMITTED → REJECTED. Доступно только INSPECTOR и ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация отклонена"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе SUBMITTED", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @PatchMapping("/reject/{id}")
    public ResponseEntity<UserDeclarationDtoResponse> rejectDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id,
            @Parameter(description = "Причина отклонения") @RequestParam String reason
    ) {
        return ResponseEntity.ok(
                userDeclarationMapper.toDtoResponse(
                        userDeclarationService.rejectDeclaration(id, reason)
                )
        );
    }
}
