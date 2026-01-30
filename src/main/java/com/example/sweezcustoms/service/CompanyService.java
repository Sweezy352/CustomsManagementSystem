package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.UserEntity;

import java.util.List;

public interface CompanyService {
    CompanyEntity createCompany(CompanyEntity companyEntity);
    CompanyEntity getCompanyById(Long id);
    CompanyEntity getByCompanyName(String name);
    List<UserEntity> getEmployees(Long companyId);
    CompanyEntity getCompanyByTin(String tin);
    CompanyEntity getCompanyByOkpo(String okpo);
    CompanyEntity getCompanyByCustomsCode(String customsCode);

}
