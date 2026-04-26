package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.CompanyDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDtoResponse;
import com.example.sweezcustoms.dto.view.CompanyDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.mapper.CompanyMapper;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.service.CompanyService;
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

@Tag(name = "Компании", description = "Создание и получение информации о компаниях")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class    CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @Operation(
            summary = "Создать компанию",
            description = "Регистрирует новую компанию для текущего аутентифицированного пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания успешно создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Компания с таким названием уже существует", content = @Content)
            }
    )
    @PostMapping("/create-company")
    public ResponseEntity<CompanyDtoResponse> createCompany(
            @Valid @RequestBody CompanyDtoRequest companyDtoRequest
    ) {
        return ResponseEntity.ok(
                companyMapper.toDtoResponse(
                        companyService.createCompany(companyMapper.toEntity(companyDtoRequest))
                )
        );
    }


    @Operation(
            summary = "Получить компанию по ID",
            description = "Доступно только владельцу или сотруднику компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания найдена"),
                    @ApiResponse(responseCode = "403", description = "Нет доступа к данной компании", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("@spEL.canAccessCompany(#id, 'id')")
    public ResponseEntity<CompanyDtoResponse> getCompanyById(
            @Parameter(description = "ID компании") @PathVariable Long id
    ) {
        return ResponseEntity.ok(companyMapper.toDtoResponse(companyService.getCompanyById(id)));
    }

    @Operation(summary = "Получить компанию по названию",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания найдена"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-company-name")
    public ResponseEntity<CompanyDtoView> getCompanyByName(
            @Parameter(description = "Название компании") @RequestParam String companyName
    ) {
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getByCompanyName(companyName)));
    }

    @Operation(summary = "Получить сотрудников компании",
            description = "Доступно только владельцу или сотруднику компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список сотрудников"),
                    @ApiResponse(responseCode = "403", description = "Нет доступа", content = @Content)
            }
    )
    @GetMapping("/get-employees-company/{id}")
    @PreAuthorize("@spEL.canAccessCompany(#id, 'id')")
    public ResponseEntity<List<UserDtoView>> getEmployeesCompany(
            @Parameter(description = "ID компании") Long id
    ) {
        return ResponseEntity.ok(userMapper.toDtoViewList(companyService.getEmployees(id)));
    }

    @Operation(summary = "Получить компанию по ИНН (TIN)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания найдена"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-tin")
    public ResponseEntity<CompanyDtoView> getCompanyByTin(
            @Parameter(description = "ИНН компании") @RequestParam String companyTin
    ) {
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getCompanyByTin(companyTin)));
    }

    @Operation(summary = "Получить компанию по ОКПО",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания найдена"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-okpo")
    public ResponseEntity<CompanyDtoView> getCompanyByOkpo(
            @Parameter(description = "ОКПО компании") @RequestParam String companyOkpo
    ) {
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getCompanyByOkpo(companyOkpo)));
    }

    @Operation(summary = "Получить компанию по таможенному коду",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Компания найдена"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-by-customs-code")
    public ResponseEntity<CompanyDtoView> getCompanyByCustomsCode(
            @Parameter(description = "Таможенный код компании") @RequestParam String companyCustomsCode
    ) {
        return ResponseEntity.ok(
                companyMapper.toDtoView(
                        companyService.getCompanyByCustomsCode(companyCustomsCode)
                )
        );
    }

    @Operation(
            summary = "Добавить сотрудника в компанию",
            description = "Привязывает пользователя к компании и назначает ему роль. Доступно только OWNER. Допустимые роли: MANAGER, ACCOUNTANT, ANALYTIC, LOGISTICIAN.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сотрудник добавлен"),
                    @ApiResponse(responseCode = "404", description = "Компания или пользователь не найден", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Нет доступа", content = @Content)
            }
    )
    @PostMapping("/{companyId}/employees/{userId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<Void> addEmployee(
            @Parameter(description = "ID компании") @PathVariable Long companyId,
            @Parameter(description = "ID пользователя") @PathVariable Long userId,
            @Parameter(description = "Роль: MANAGER, ACCOUNTANT, ANALYTIC, LOGISTICIAN") @RequestParam String roleName
    ) {
        companyService.addEmployee(companyId, userId, roleName);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Удалить сотрудника из компании",
            description = "Отвязывает пользователя от компании и снимает корпоративные роли. Доступно только OWNER.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Сотрудник удалён"),
                    @ApiResponse(responseCode = "404", description = "Компания или пользователь не найден", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Нет доступа", content = @Content)
            }
    )
    @DeleteMapping("/{companyId}/employees/{userId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<Void> removeEmployee(
            @Parameter(description = "ID компании") @PathVariable Long companyId,
            @Parameter(description = "ID пользователя") @PathVariable Long userId
    ) {
        companyService.removeEmployee(companyId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить компании на ожидании верификации",
            description = "Доступно только INSPECTOR и ADMIN.",
            responses = @ApiResponse(responseCode = "200", description = "Список компаний")
    )
    @GetMapping("/pending")
    public ResponseEntity<List<CompanyDtoView>> getPendingCompanies() {
        return ResponseEntity.ok(companyMapper.toDtoViewList(companyService.getPendingCompanies()));
    }

    @Operation(
            summary = "Верифицировать / отклонить компанию",
            description = "Инспектор меняет статус PENDING → APPROVED или REJECTED. Доступно только INSPECTOR и ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус обновлён"),
                    @ApiResponse(responseCode = "400", description = "Компания не в статусе PENDING", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @PatchMapping("/verify/{companyId}")
    public ResponseEntity<CompanyDtoResponse> verifyCompany(
            @Parameter(description = "ID компании") @PathVariable Long companyId,
            @Parameter(description = "true — одобрить, false — отклонить") @RequestParam boolean approve
    ) {
        return ResponseEntity.ok(companyMapper.toDtoResponse(companyService.verifyCompany(companyId, approve)));
    }
}
