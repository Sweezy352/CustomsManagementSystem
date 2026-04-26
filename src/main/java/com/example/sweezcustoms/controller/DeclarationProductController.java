package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.DeclarationProductDtoRequest;
import com.example.sweezcustoms.dto.response.DeclarationProductDto;
import com.example.sweezcustoms.mapper.DeclarationProductMapper;
import com.example.sweezcustoms.service.DeclarationProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Товары в декларации", description = "Добавление и управление товарами внутри таможенной декларации. При добавлении товара автоматически рассчитываются НДС, таможенная пошлина и акциз на основе кода ТН ВЭД")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/declaration-products")
@RequiredArgsConstructor
public class DeclarationProductController {
    private final DeclarationProductService declarationProductService;
    private final DeclarationProductMapper declarationProductMapper;

    @Operation(
            summary = "Добавить товар в декларацию",
            description = "Добавляет товар к существующей декларации (компании или физлица). " +
                    "Если указан код ТН ВЭД, автоматически рассчитываются НДС, таможенная пошлина и акциз от поля totalPrice.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Декларация или код ТН ВЭД не найден", content = @Content)
            }
    )
    @PostMapping("/add/{declarationId}")
    public ResponseEntity<DeclarationProductDto> addToDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long declarationId,
            @Valid @RequestBody DeclarationProductDtoRequest request
    ) {
        return ResponseEntity.ok(
                declarationProductMapper.toDtoResponse(
                        declarationProductService.addToDeclaration(
                                declarationId,
                                declarationProductMapper.toEntity(request),
                                request.getTnvedCode()
                        )
                )
        );
    }

    @Operation(
            summary = "Получить товар по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар найден"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден", content = @Content)
            }
    )
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<DeclarationProductDto> getById(
            @Parameter(description = "ID товара") @PathVariable Long id
    ) {
        return ResponseEntity.ok(declarationProductMapper.toDtoResponse(declarationProductService.getById(id)));
    }

    @Operation(
            summary = "Получить все товары декларации",
            description = "Возвращает список всех товаров, привязанных к указанной декларации.",
            responses = @ApiResponse(responseCode = "200", description = "Список товаров")
    )
    @GetMapping("/get-all/{declarationId}")
    public ResponseEntity<List<DeclarationProductDto>> getAllByDeclaration(
            @Parameter(description = "ID декларации") @PathVariable Long declarationId
    ) {
        return ResponseEntity.ok(
                declarationProductMapper.toDtoResponseList(
                        declarationProductService.getAllByDeclarationId(declarationId)
                )
        );
    }

    @Operation(
            summary = "Обновить товар",
            description = "Обновляет данные товара и пересчитывает налоги если изменился код ТН ВЭД или totalPrice.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар обновлён"),
                    @ApiResponse(responseCode = "404", description = "Товар или код ТН ВЭД не найден", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<DeclarationProductDto> update(
            @Parameter(description = "ID товара") @PathVariable Long id,
            @Valid @RequestBody DeclarationProductDtoRequest request
    ) {
        return ResponseEntity.ok(
                declarationProductMapper.toDtoResponse(
                        declarationProductService.update(
                                id,
                                declarationProductMapper.toEntity(request),
                                request.getTnvedCode()
                        )
                )
        );
    }

    @Operation(
            summary = "Удалить товар из декларации",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Товар удалён"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID товара") @PathVariable Long id
    ) {
        declarationProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
