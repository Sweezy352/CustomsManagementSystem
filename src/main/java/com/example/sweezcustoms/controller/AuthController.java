package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.security.AuthenticationToken;
import com.example.sweezcustoms.security.AuthenticationTokenRequest;
import com.example.sweezcustoms.security.PasswordConfirmation;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.utils.InternalizationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Аутентификация", description = "Регистрация, вход, обновление токенов и восстановление пароля")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;
    private final InternalizationHelper internalizationHelper;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создаёт нового пользователя. Требует фото и подпись в виде multipart-файлов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных", content = @Content)
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDtoResponse> register(
            @Valid @RequestPart("userDtoRequest") UserDtoRequest userDtoRequest,
            @Parameter(description = "Фото пользователя") @RequestParam MultipartFile photoUser,
            @Parameter(description = "Подпись пользователя") @RequestParam MultipartFile signatureUser
    ) throws BaseException {
        return ResponseEntity.ok(
                userMapper.toDtoResponse(
                        authService.register(userMapper.toEntity(userDtoRequest), photoUser, signatureUser, userDtoRequest.getRoleName())
                )
        );
    }

    @Operation(
            summary = "Вход в систему",
            description = "Возвращает JWT access и refresh токены.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный вход"),
                    @ApiResponse(responseCode = "401", description = "Неверный email или пароль", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> login(
            @Valid @RequestBody AuthenticationTokenRequest authenticationTokenRequest
    ) throws BaseException {
        return ResponseEntity.ok(authService.login(authenticationTokenRequest));
    }

    @Operation(
            summary = "Обновление токена",
            description = "Принимает refresh-токен в заголовке Authorization (Bearer) и возвращает новую пару токенов.",
            security = @SecurityRequirement(name = "BearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Токены успешно обновлены"),
                    @ApiResponse(responseCode = "401", description = "Невалидный или истёкший refresh-токен", content = @Content)
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationToken> refreshToken(HttpServletRequest request) throws BaseException {
        String refreshToken = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @Operation(
            summary = "Запрос восстановления пароля",
            description = "Отправляет письмо с кодом подтверждения на указанный email.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Письмо отправлено"),
                    @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден", content = @Content)
            }
    )
    @PostMapping("/password-recovery")
    public void resetPassword(@Parameter(description = "Email пользователя") @RequestParam String email) throws BaseException {
        authService.passwordRecovery(email);
    }

    @Operation(
            summary = "Сброс пароля",
            description = "Устанавливает новый пароль по коду подтверждения из письма.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пароль успешно изменён"),
                    @ApiResponse(responseCode = "400", description = "Неверный или истёкший код", content = @Content)
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Parameter(description = "Код подтверждения из письма") @RequestParam String code,
            @Valid @RequestBody PasswordConfirmation passwordConfirmation
    ) throws BaseException {
        authService.resetPassword(code, passwordConfirmation);
        return ResponseEntity.ok().body(internalizationHelper.getTranslation("password.reset.succeed"));
    }

    @Operation(summary = "Тест токенов", description = "Проверяет работоспособность JWT-фильтра.", security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/test-tokens")
    public String testTokens() {
        return "Все работает";
    }
}
