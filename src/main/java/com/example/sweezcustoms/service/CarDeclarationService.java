package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.CarDeclaration;

import java.util.List;

public interface CarDeclarationService {
    CarDeclaration createForUser(CarDeclaration carDeclaration);
    CarDeclaration createForCompany(Long companyId, CarDeclaration carDeclaration);
    CarDeclaration getById(Long id);
    CarDeclaration getByVin(String vinCode);
    List<CarDeclaration> getAllByUser();
    List<CarDeclaration> getAllByCompany(Long companyId);
    CarDeclaration submitDeclaration(Long id);
    CarDeclaration approveDeclaration(Long id);
    CarDeclaration rejectDeclaration(Long id, String reason);
}
