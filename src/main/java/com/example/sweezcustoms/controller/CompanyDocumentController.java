package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.service.CompanyDocumentService;
import com.example.sweezcustoms.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/company-document")
@RequiredArgsConstructor
public class CompanyDocumentController {
    private final CompanyDocumentService companyDocumentService;
    private final CompanyService companyService;


    @GetMapping("/get-document-registration")
    @PreAuthorize("@spEL.canAccessCompany(#companyTin, 'companyTin')")
    public ResponseEntity<byte[]> getDocumentRegistration(@RequestParam String companyTin, @RequestParam String documentType, @RequestParam String language){
        byte[] pdfBytes = companyDocumentService.getCertificateDocument(companyService.getCompanyByTin(companyTin), CompanyDocumentType.valueOf(documentType) , language);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("certificate.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
