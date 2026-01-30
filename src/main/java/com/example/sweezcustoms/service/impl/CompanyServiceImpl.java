package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.exceptions.CompanyNotFoundException;
import com.example.sweezcustoms.exceptions.CompanyWithSuchNameAlreadyExists;
import com.example.sweezcustoms.exceptions.NullEmployeesInCompany;
import com.example.sweezcustoms.repository.CompanyRepository;
import com.example.sweezcustoms.service.CompanyService;
import com.example.sweezcustoms.utils.BusinessIdentityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final BusinessIdentityGenerator businessIdentityGenerator;

    @Override
    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        CompanyEntity companyFound = companyRepository.findByName(companyEntity.getName()).orElse(null);
        if(Objects.nonNull(companyFound)) throw new CompanyWithSuchNameAlreadyExists("");
        companyEntity.setTin(businessIdentityGenerator.generateTin());
        companyEntity.setOkpo(businessIdentityGenerator.generateOkpo());
        companyEntity.setCustomsCode(businessIdentityGenerator.generateCustomsCode(companyEntity.getTin()));
        companyEntity.setStatus(CustomsStatusEnum.APPROVED);
        return companyRepository.save(companyEntity);
    }

    @Override
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    @Override
    public CompanyEntity getByCompanyName(String name) {
        return companyRepository.findByName(name).orElseThrow(() -> new CompanyNotFoundException(""));
    }

    @Override
    public List<UserEntity> getEmployees(Long companyId) {
        CompanyEntity companyEntity = getCompanyById(companyId);
        if(companyEntity.getEmployees().isEmpty()) throw new NullEmployeesInCompany("");
        return companyEntity.getEmployees();
    }

    @Override
    public CompanyEntity getCompanyByTin(String tin) {
        return companyRepository.findByTin(tin).orElseThrow(() -> new CompanyNotFoundException(""));
    }

    @Override
    public CompanyEntity getCompanyByOkpo(String okpo) {
        return companyRepository.findByOkpo(okpo).orElseThrow(() -> new CompanyNotFoundException(""));
    }

    @Override
    public CompanyEntity getCompanyByCustomsCode(String customsCode) {
        return companyRepository.findByCustomsCode(customsCode).orElseThrow(() -> new CompanyNotFoundException(""));
    }
}
