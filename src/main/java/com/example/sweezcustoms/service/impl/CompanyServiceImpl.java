package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.exceptions.CompanyNotFoundException;
import com.example.sweezcustoms.exceptions.CompanyWithSuchNameAlreadyExists;
import com.example.sweezcustoms.exceptions.NullEmployeesInCompany;
import com.example.sweezcustoms.repository.CompanyRepository;
import com.example.sweezcustoms.repository.RoleRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.CompanyService;
import com.example.sweezcustoms.utils.BusinessIdentityGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final BusinessIdentityGenerator businessIdentityGenerator;
    private final AuthService authService;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        boolean companyFound = companyRepository.existsByName(companyEntity.getName());
        if(companyFound) throw new CompanyWithSuchNameAlreadyExists("company.with.such.name.already.exists");

        UserEntity userEntity = authService.getCurrent();
        companyEntity.setOwner(userEntity);
        userEntity.getRoles().add(roleRepository.findByRoleName("OWNER").orElseThrow(() -> new RuntimeException("Role not found")));

        companyEntity.setTin(businessIdentityGenerator.generateTin());
        companyEntity.setOkpo(businessIdentityGenerator.generateOkpo());
        companyEntity.setCustomsCode(businessIdentityGenerator.generateCustomsCode(companyEntity.getTin()));
        companyEntity.setStatus(CustomsStatusEnum.APPROVED);
        companyEntity.setVerifiedAt(LocalDateTime.now());

        return companyRepository.save(companyEntity);
    }

    @Override
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getByCompanyName(String name) {
        return companyRepository.findByName(name).orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public List<UserEntity> getEmployees(Long companyId) {
        CompanyEntity companyEntity = getCompanyById(companyId);
        if(companyEntity.getEmployees().isEmpty()) throw new NullEmployeesInCompany("null.employees.company");
        return companyEntity.getEmployees();
    }

    @Override
    public CompanyEntity getCompanyByTin(String tin) {
        return companyRepository.findByTin(tin).orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getCompanyByOkpo(String okpo) {
        return companyRepository.findByOkpo(okpo).orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getCompanyByCustomsCode(String customsCode) {
        return companyRepository.findByCustomsCode(customsCode).orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }
}
