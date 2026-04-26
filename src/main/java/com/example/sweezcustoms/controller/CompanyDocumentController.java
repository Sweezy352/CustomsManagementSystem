package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.service.CompanyDocumentService;
import com.example.sweezcustoms.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Tag(name = "Документы компании", description = "Генерация и скачивание официальных документов компании в формате PDF")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/company-document")
@RequiredArgsConstructor
public class CompanyDocumentController {
    private final CompanyDocumentService companyDocumentService;
    private final CompanyService companyService;


    @Operation(
            summary = "Скачать регистрационный документ компании",
            description = "Генерирует PDF-документ указанного типа для компании. Доступен только владельцу или сотруднику компании.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF-файл документа",
                            content = @Content(mediaType = "application/pdf")),
                    @ApiResponse(responseCode = "403", description = "Нет доступа к документам компании", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content)
            }
    )
    @GetMapping("/get-document-registration")
    @PreAuthorize("@spEL.canAccessCompany(#companyTin, 'companyTin')")
    public ResponseEntity<byte[]> getDocumentRegistration(
            @Parameter(description = "ИНН компании") @RequestParam String companyTin,
            @Parameter(description = "Тип документа (enum CompanyDocumentType, например: COMPANY_REG_CERTIFICATE)") @RequestParam String documentType,
            @Parameter(description = "Язык документа: ru, en, kg") @RequestParam String language
    ) {
        byte[] pdfBytes = companyDocumentService.getCertificateDocument(
                companyService.getCompanyByTin(companyTin),
                CompanyDocumentType.valueOf(documentType),
                language
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("certificate.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
