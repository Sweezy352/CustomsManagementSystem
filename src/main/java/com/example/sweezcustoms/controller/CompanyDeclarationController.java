package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.CompanyDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDeclarationDtoResponse;
import com.example.sweezcustoms.entity.CompanyDeclaration;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.mapper.CompanyDeclarationMapper;
import com.example.sweezcustoms.service.CompanyDeclarationService;
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

@Tag(name = "Декларации компаний", description = "Создание и получение таможенных деклараций компаний")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/company-declarations")
@RequiredArgsConstructor
public class CompanyDeclarationController {
    private final CompanyDeclarationService companyDeclarationService;
    private final CompanyDeclarationMapper companyDeclarationMapper;

    @Operation(
            summary = "Создать декларацию",
            description = "Создаёт новую таможенную декларацию для компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация успешно создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<CompanyDeclarationDtoResponse> createDeclaration(
            @RequestBody CompanyDeclarationDtoRequest request
    ) throws BaseException {
        System.out.println("request: declarationType: " + request.getDeclarationType().name());
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.createDeclaration(
                                companyDeclarationMapper.toEntity(request)
                        )
                )
        );
    }

    @Operation(
            summary = "Получить все декларации компании",
            description = "Возвращает полный список деклараций для указанной компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список деклараций"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-all/{companyId}")
    public ResponseEntity<List<CompanyDeclarationDtoResponse>> getAllDeclarations(
            @Parameter(description = "ID компании") @PathVariable Long companyId
    ) throws BaseException {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponseList(
                        companyDeclarationService.getFullCompanyDeclarations(companyId)
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
    public ResponseEntity<CompanyDeclarationDtoResponse> getById(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.getDeclarationById(id)
                )
        );
    }

    @Operation(
            summary = "Обновить декларацию",
            description = "Обновляет данные существующей декларации компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация обновлена"),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @PutMapping("/update-declaration/{id}")
    public ResponseEntity<CompanyDeclarationDtoResponse> updateDeclaration(
            @RequestBody CompanyDeclarationDtoRequest request,
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) throws BaseException {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.updateDeclaration(
                                id,
                                companyDeclarationMapper.toEntity(request)
                        )
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
    public ResponseEntity<CompanyDeclarationDtoResponse> submitDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.submitDeclaration(id)
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
    public ResponseEntity<CompanyDeclarationDtoResponse> approveDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.approveDeclaration(id)
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
    public ResponseEntity<CompanyDeclarationDtoResponse> rejectDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long id,
            @Parameter(description = "Причина отклонения") @RequestParam String reason
    ) {
        return ResponseEntity.ok(
                companyDeclarationMapper.toDtoResponse(
                        companyDeclarationService.rejectDeclaration(id, reason)
                )
        );
    }
}
