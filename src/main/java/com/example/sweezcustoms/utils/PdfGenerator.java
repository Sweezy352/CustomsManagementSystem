package com.example.sweezcustoms.utils;

import com.example.sweezcustoms.service.QrCodeService;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PdfGenerator {
    private final QrCodeService qrCodeService;
    private final TemplateEngine templateEngine;

    public byte[] generatePdfFile(String html){
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public byte[] generateCertificate(Map<String, Object> variables, String documentType, String lang, String qrCodeUrl){
        Locale locale = Locale.forLanguageTag(lang);
        Context context = new Context(locale);
        try {
            byte[] qrCode = qrCodeService.generateQrCode(qrCodeUrl, 200, 200);

            String qrCodeBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(qrCode);
            context.setVariable("qrCodeBase64", qrCodeBase64);
            context.setVariables(variables);

            String html = templateEngine.process(documentType.toLowerCase(), context);
            return generatePdfFile(html);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
