package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.CompanyDocumentEntity;
import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;

import java.util.Locale;

public interface CompanyDocumentService {
    byte[] generateCertificate(CompanyEntity companyEntity, CompanyDocumentType documentType ,String lang);
    byte[] createDocument(CompanyEntity companyEntity, CompanyDocumentType companyDocumentType, String lang);
    byte[] getCertificateDocument(CompanyEntity companyEntity, CompanyDocumentType documentType ,String lang);
}
