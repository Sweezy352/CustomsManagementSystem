package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.TnvedCodeDtoRequest;
import com.example.sweezcustoms.dto.response.TnvedCodeDtoResponse;
import com.example.sweezcustoms.dto.view.TnvedCodeDtoView;
import com.example.sweezcustoms.mapper.TnvedCodeMapper;
import com.example.sweezcustoms.service.TnvedCodeService;
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

@Tag(name = "Коды ТН ВЭД", description = "Управление справочником кодов ТН ВЭД — таможенная классификация товаров со ставками пошлин, НДС и акциза")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/tnved")
@RequiredArgsConstructor
public class TnvedCodeController {
    private final TnvedCodeService tnvedCodeService;
    private final TnvedCodeMapper tnvedCodeMapper;

    @Operation(
            summary = "Добавить код ТН ВЭД",
            description = "Создаёт новую запись в справочнике ТН ВЭД. Доступно только администраторам.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Код успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Нет доступа", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<TnvedCodeDtoResponse> create(@Valid @RequestBody TnvedCodeDtoRequest request) {
        return ResponseEntity.ok(
                tnvedCodeMapper.toDtoResponse(
                        tnvedCodeService.create(tnvedCodeMapper.toEntity(request))
                )
        );
    }

    @Operation(
            summary = "Получить код ТН ВЭД по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Код найден"),
                    @ApiResponse(responseCode = "404", description = "Код не найден", content = @Content)
            }
    )
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<TnvedCodeDtoResponse> getById(
            @Parameter(description = "ID записи") @PathVariable Long id
    ) {
        return ResponseEntity.ok(tnvedCodeMapper.toDtoResponse(tnvedCodeService.getById(id)));
    }

    @Operation(
            summary = "Найти по коду ТН ВЭД",
            description = "Поиск по точному значению кода, например: 8517120000",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Код найден"),
                    @ApiResponse(responseCode = "404", description = "Код не найден", content = @Content)
            }
    )
    @GetMapping("/get-by-code")
    public ResponseEntity<TnvedCodeDtoResponse> getByCode(
            @Parameter(description = "Код ТН ВЭД", example = "8517120000") @RequestParam String code
    ) {
        return ResponseEntity.ok(tnvedCodeMapper.toDtoResponse(tnvedCodeService.getByCode(code)));
    }

    @Operation(
            summary = "Получить весь справочник ТН ВЭД",
            responses = @ApiResponse(responseCode = "200", description = "Список кодов")
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<TnvedCodeDtoView>> getAll() {
        return ResponseEntity.ok(tnvedCodeMapper.toDtoViewList(tnvedCodeService.getAll()));
    }

    @Operation(
            summary = "Обновить код ТН ВЭД",
            description = "Обновляет ставки и описание существующего кода. Доступно только администраторам.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Код обновлён"),
                    @ApiResponse(responseCode = "404", description = "Код не найден", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<TnvedCodeDtoResponse> update(
            @Parameter(description = "ID записи") @PathVariable Long id,
            @Valid @RequestBody TnvedCodeDtoRequest request
    ) {
        return ResponseEntity.ok(
                tnvedCodeMapper.toDtoResponse(
                        tnvedCodeService.update(id, tnvedCodeMapper.toEntity(request))
                )
        );
    }

    @Operation(
            summary = "Удалить код ТН ВЭД",
            description = "Удаляет запись из справочника. Доступно только администраторам.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Код удалён"),
                    @ApiResponse(responseCode = "404", description = "Код не найден", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID записи") @PathVariable Long id
    ) {
        tnvedCodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
