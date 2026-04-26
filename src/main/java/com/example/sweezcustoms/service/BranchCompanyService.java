package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.BranchCompanyEntity;

import java.util.List;

public interface BranchCompanyService {
    BranchCompanyEntity create(Long companyId, BranchCompanyEntity branch);
    List<BranchCompanyEntity> getAllByCompany(Long companyId);
    BranchCompanyEntity getById(Long id);
    BranchCompanyEntity update(Long id, BranchCompanyEntity branch);
    void delete(Long id);
}
