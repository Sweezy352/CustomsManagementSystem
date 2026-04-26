package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.CompanyDeclaration;

import java.util.List;

public interface CompanyDeclarationService {
    CompanyDeclaration createDeclaration(CompanyDeclaration companyDeclaration);
    List<CompanyDeclaration> getAllDeclarations();
    List<CompanyDeclaration> getFullCompanyDeclarations(Long companyId);
    CompanyDeclaration getDeclarationById(Long id);
    CompanyDeclaration updateDeclaration(Long declarationId, CompanyDeclaration companyDeclaration);
    CompanyDeclaration submitDeclaration(Long id);
    CompanyDeclaration approveDeclaration(Long id);
    CompanyDeclaration rejectDeclaration(Long id, String reason);
}
