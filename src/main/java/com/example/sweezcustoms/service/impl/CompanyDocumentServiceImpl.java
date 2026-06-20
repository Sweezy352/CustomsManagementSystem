package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyDocumentEntity;
import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.repository.CompanyDocumentRepository;
import com.example.sweezcustoms.service.CompanyDocumentService;
import com.example.sweezcustoms.service.MinIoService;
import com.example.sweezcustoms.service.QrCodeService;
import com.example.sweezcustoms.utils.InternalizationHelper;
import com.example.sweezcustoms.utils.PdfGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:custom-urls.properties")
@Transactional
public class CompanyDocumentServiceImpl implements CompanyDocumentService {
    @Value("${minio.bucket.name.company-documents}")
    private String bucketName;
    private final PdfGenerator pdfGenerator;
    private final QrCodeService qrCodeService;
    private final SpringTemplateEngine templateEngine;
    private final CompanyDocumentRepository companyDocumentRepository;
    private final MinIoService minIoService;
    @Value("${company-registration-verification-url}")
    private String documentVerificationAddress;

    @Override
    public byte[] generateCertificate(CompanyEntity companyEntity, CompanyDocumentType documentType ,String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        Context context = new Context(locale);
        try {
            byte[] qrCode = qrCodeService.generateQrCode(documentVerificationAddress, 200, 200);

            String qrCodeBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(qrCode);
            context.setVariable("company", companyEntity);
            context.setVariable("issueDate", companyEntity.getVerifiedAt());
            context.setVariable("qrCodeBase64", qrCodeBase64);

            String html = templateEngine.process(documentType.name().toLowerCase(), context);
            return pdfGenerator.generatePdfFile(html);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public byte[] createDocument(CompanyEntity companyEntity, CompanyDocumentType companyDocumentType, String lang){
        Map<String, Object> variables = new HashMap<>();
        variables.put("company", companyEntity);
        variables.put("issueDate", companyEntity.getVerifiedAt());

        byte[] pdfGenerated = generateCertificate(
                companyEntity,
                companyDocumentType,
                lang);

        String objectName = String.format("companies_%d_cert_%s.pdf", companyEntity.getId(), lang);
        minIoService.uploadWithBytes(bucketName, pdfGenerated, objectName, "application/pdf");
        CompanyDocumentEntity companyDocumentEntity = CompanyDocumentEntity
                .builder()
                .companyDocumentType(companyDocumentType)
                .companyEntity(companyEntity)
                .fileName(objectName)
                .status(CustomsStatusEnum.APPROVED)
                .language(lang)
                .build();
        companyDocumentRepository.save(companyDocumentEntity);

        return pdfGenerated;
    }

    @Override
    public byte[] getCertificateDocument(CompanyEntity companyEntity, CompanyDocumentType documentType ,String lang) {
        String objectName = String.format("companies_%d_cert_%s.pdf", companyEntity.getId(), lang);
        return companyDocumentRepository.findByFileName(objectName).map(document -> {
            return minIoService.downloadFile(bucketName, objectName);
        }).orElseGet(() -> createDocument(companyEntity, documentType, lang));
    }
}
