package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.RoleEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.enums.CompanyDocumentType;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import com.example.sweezcustoms.exceptions.CompanyNotFoundException;
import com.example.sweezcustoms.exceptions.CompanyWithSuchNameAlreadyExists;
import com.example.sweezcustoms.exceptions.NullEmployeesInCompany;
import com.example.sweezcustoms.exceptions.UserNotFoundException;
import com.example.sweezcustoms.exceptions.BaseException;
import com.example.sweezcustoms.exceptions.ErrorBody;
import com.example.sweezcustoms.repository.CompanyRepository;
import com.example.sweezcustoms.repository.RoleRepository;
import com.example.sweezcustoms.repository.UserRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.CompanyDocumentService;
import com.example.sweezcustoms.service.CompanyService;
import com.example.sweezcustoms.utils.BusinessIdentityGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final UserRepository userRepository;
    private final CompanyDocumentService companyDocumentService;

    @Override
    @Transactional
    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        boolean companyFound = companyRepository.existsByName(companyEntity.getName());
        if (companyFound) throw new CompanyWithSuchNameAlreadyExists("company.with.such.name.already.exists");

        UserEntity userEntity = authService.getCurrent();
        companyEntity.setOwner(userEntity);
        RoleEntity ownerRole = roleRepository.findByRoleName("OWNER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        if (userEntity.getRoles() == null) {
            userEntity.setRoles(new java.util.ArrayList<>());
        }
        userEntity.getRoles().add(ownerRole);

        companyEntity.setTin(businessIdentityGenerator.generateTin());
        companyEntity.setOkpo(businessIdentityGenerator.generateOkpo());
        companyEntity.setCustomsCode(businessIdentityGenerator.generateCustomsCode(companyEntity.getTin()));
        companyEntity.setStatus(CustomsStatusEnum.PENDING);
        companyEntity.setVerifiedAt(null);

        CompanyEntity saved = companyRepository.save(companyEntity);
        userEntity.setCompanyEntity(saved);
        return saved;
    }

    @Override
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getByCompanyName(String name) {
        return companyRepository.findByName(name)
                .orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public List<UserEntity> getEmployees(Long companyId) {
        CompanyEntity companyEntity = getCompanyById(companyId);
        if (companyEntity.getEmployees().isEmpty()) throw new NullEmployeesInCompany("null.employees.company");
        return companyEntity.getEmployees();
    }

    @Override
    public CompanyEntity getCompanyByTin(String tin) {
        return companyRepository.findByTin(tin)
                .orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getCompanyByOkpo(String okpo) {
        return companyRepository.findByOkpo(okpo)
                .orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    public CompanyEntity getCompanyByCustomsCode(String customsCode) {
        return companyRepository.findByCustomsCode(customsCode)
                .orElseThrow(() -> new CompanyNotFoundException("company.not.found"));
    }

    @Override
    @Transactional
    public void addEmployee(Long companyId, Long userId, String roleName) {
        CompanyEntity company = getCompanyById(companyId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user.not.found"));
        RoleEntity role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setCompanyEntity(company);
        if (user.getRoles() == null) {
            user.setRoles(new java.util.ArrayList<>());
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeEmployee(Long companyId, Long userId) {
        CompanyEntity company = getCompanyById(companyId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user.not.found"));
        user.setCompanyEntity(null);
        user.getRoles().removeIf(r -> List.of("MANAGER", "ACCOUNTANT", "ANALYTIC", "LOGISTICIAN").contains(r.getRoleName()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public CompanyEntity verifyCompany(Long companyId, boolean approve) {
        CompanyEntity company = getCompanyById(companyId);
        if (company.getStatus() != CustomsStatusEnum.PENDING) {
            throw new BaseException(ErrorBody.builder().code(400).message("company.not.pending").build());
        }
        UserEntity inspector = authService.getCurrent();
        company.setStatus(approve ? CustomsStatusEnum.APPROVED : CustomsStatusEnum.REJECTED);
        company.setVerifiedBy(inspector);
        company.setVerifiedAt(LocalDateTime.now());
        company = companyRepository.save(company);
        companyDocumentService.createDocument(company, CompanyDocumentType.COMPANY_REG_CERTIFICATE, LocaleContextHolder.getLocale().toString());
        return company;
    }

    @Override
    public List<CompanyEntity> getPendingCompanies() {
        return companyRepository.findByStatus(CustomsStatusEnum.PENDING)
                .orElse(List.of());
    }
}
