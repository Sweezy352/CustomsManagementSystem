package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.CarDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.CarDeclarationDtoResponse;
import com.example.sweezcustoms.mapper.CarDeclarationMapper;
import com.example.sweezcustoms.service.CarDeclarationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Декларации на автомобили",
        description = "Таможенное оформление автомобилей. Пошлины рассчитываются автоматически " +
                "на основе типа топлива, объёма двигателя и года выпуска по стандарту ЕАЭС. " +
                "Электромобили имеют льготную ставку (пошлина 0%, акциз 0%).")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/car-declarations")
@RequiredArgsConstructor
public class CarDeclarationController {

    private final CarDeclarationService carDeclarationService;
    private final CarDeclarationMapper carDeclarationMapper;

    @Operation(
            summary = "Создать декларацию на авто (физлицо)",
            description = "Создаёт декларацию для текущего авторизованного пользователя. " +
                    "Таможенные платежи рассчитываются автоматически.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация создана с рассчитанными платежами"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content)
            }
    )
    @PostMapping("/user")
    public ResponseEntity<CarDeclarationDtoResponse> createForUser(
            @Valid @RequestBody CarDeclarationDtoRequest request
    ) {
        return ResponseEntity.ok(
                carDeclarationMapper.toDtoResponse(
                        carDeclarationService.createForUser(carDeclarationMapper.toEntity(request))
                )
        );
    }

    @Operation(
            summary = "Создать декларацию на авто (компания)",
            description = "Создаёт декларацию от имени компании. Доступно OWNER и MANAGER.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация создана"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @PostMapping("/company/{companyId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<CarDeclarationDtoResponse> createForCompany(
            @Parameter(description = "ID компании") @PathVariable Long companyId,
            @Valid @RequestBody CarDeclarationDtoRequest request
    ) {
        return ResponseEntity.ok(
                carDeclarationMapper.toDtoResponse(
                        carDeclarationService.createForCompany(companyId, carDeclarationMapper.toEntity(request))
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
    public ResponseEntity<CarDeclarationDtoResponse> getById(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponse(carDeclarationService.getById(id)));
    }

    @Operation(
            summary = "Найти декларацию по VIN-коду",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация найдена"),
                    @ApiResponse(responseCode = "404", description = "Декларация не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-vin")
    public ResponseEntity<CarDeclarationDtoResponse> getByVin(
            @Parameter(description = "VIN-код автомобиля", example = "1HGBH41JXMN109186") @RequestParam String vinCode
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponse(carDeclarationService.getByVin(vinCode)));
    }

    @Operation(
            summary = "Получить все декларации текущего пользователя",
            responses = @ApiResponse(responseCode = "200", description = "Список деклараций")
    )
    @GetMapping("/my")
    public ResponseEntity<List<CarDeclarationDtoResponse>> getAllByUser() {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponseList(carDeclarationService.getAllByUser()));
    }

    @Operation(
            summary = "Получить все декларации компании",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список деклараций"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/company/{companyId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<List<CarDeclarationDtoResponse>> getAllByCompany(
            @Parameter(description = "ID компании") @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponseList(carDeclarationService.getAllByCompany(companyId)));
    }

    @Operation(
            summary = "Подать декларацию на проверку",
            description = "Меняет статус DRAFT → SUBMITTED и генерирует платёжный счёт.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация подана"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе DRAFT", content = @Content)
            }
    )
    @PatchMapping("/submit/{id}")
    public ResponseEntity<CarDeclarationDtoResponse> submit(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponse(carDeclarationService.submitDeclaration(id)));
    }

    @Operation(
            summary = "Одобрить декларацию",
            description = "Инспектор меняет статус SUBMITTED → APPROVED. Доступно INSPECTOR и ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация одобрена"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе SUBMITTED", content = @Content)
            }
    )
    @PatchMapping("/approve/{id}")
    public ResponseEntity<CarDeclarationDtoResponse> approve(
            @Parameter(description = "ID декларации") @PathVariable Long id
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponse(carDeclarationService.approveDeclaration(id)));
    }

    @Operation(
            summary = "Отклонить декларацию",
            description = "Инспектор меняет статус SUBMITTED → REJECTED. Доступно INSPECTOR и ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Декларация отклонена"),
                    @ApiResponse(responseCode = "400", description = "Декларация не в статусе SUBMITTED", content = @Content)
            }
    )
    @PatchMapping("/reject/{id}")
    public ResponseEntity<CarDeclarationDtoResponse> reject(
            @Parameter(description = "ID декларации") @PathVariable Long id,
            @Parameter(description = "Причина отклонения") @RequestParam String reason
    ) {
        return ResponseEntity.ok(carDeclarationMapper.toDtoResponse(carDeclarationService.rejectDeclaration(id, reason)));
    }
}
