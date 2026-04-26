package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.view.BranchCompanyDtoView;
import com.example.sweezcustoms.mapper.BranchCompanyMapper;
import com.example.sweezcustoms.service.BranchCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Филиалы компании", description = "Управление филиалами компании")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchCompanyController {
    private final BranchCompanyService branchCompanyService;
    private final BranchCompanyMapper branchCompanyMapper;

    @Operation(
            summary = "Создать филиал",
            description = "Создаёт новый филиал для указанной компании. Доступно только OWNER.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Филиал создан"),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @PostMapping("/{companyId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<BranchCompanyDtoView> create(
            @Parameter(description = "ID компании") @PathVariable Long companyId,
            @RequestBody BranchCompanyDtoView request
    ) {
        return ResponseEntity.ok(
                branchCompanyMapper.toDtoView(
                        branchCompanyService.create(companyId, branchCompanyMapper.toEntity(request))
                )
        );
    }

    @Operation(
            summary = "Получить все филиалы компании",
            responses = @ApiResponse(responseCode = "200", description = "Список филиалов")
    )
    @GetMapping("/{companyId}")
    @PreAuthorize("@spEL.canAccessCompany(#companyId, 'id')")
    public ResponseEntity<List<BranchCompanyDtoView>> getAllByCompany(
            @Parameter(description = "ID компании") @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(
                branchCompanyMapper.toDtoViewList(branchCompanyService.getAllByCompany(companyId))
        );
    }

    @Operation(
            summary = "Обновить филиал",
            description = "Обновляет название, адрес и телефон филиала. Доступно только OWNER.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Филиал обновлён"),
                    @ApiResponse(responseCode = "404", description = "Филиал не найден", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<BranchCompanyDtoView> update(
            @Parameter(description = "ID филиала") @PathVariable Long id,
            @RequestBody BranchCompanyDtoView request
    ) {
        return ResponseEntity.ok(
                branchCompanyMapper.toDtoView(
                        branchCompanyService.update(id, branchCompanyMapper.toEntity(request))
                )
        );
    }

    @Operation(
            summary = "Удалить филиал",
            description = "Удаляет филиал компании. Доступно только OWNER.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Филиал удалён"),
                    @ApiResponse(responseCode = "404", description = "Филиал не найден", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID филиала") @PathVariable Long id
    ) {
        branchCompanyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
