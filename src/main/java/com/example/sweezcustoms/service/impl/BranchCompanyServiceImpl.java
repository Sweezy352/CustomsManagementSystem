package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.BranchCompanyEntity;
import com.example.sweezcustoms.exceptions.BranchNotFoundException;
import com.example.sweezcustoms.repository.BranchCompanyRepository;
import com.example.sweezcustoms.service.BranchCompanyService;
import com.example.sweezcustoms.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchCompanyServiceImpl implements BranchCompanyService {
    private final BranchCompanyRepository branchCompanyRepository;
    private final CompanyService companyService;

    @Override
    public BranchCompanyEntity create(Long companyId, BranchCompanyEntity branch) {
        branch.setCompanyEntity(companyService.getCompanyById(companyId));
        return branchCompanyRepository.save(branch);
    }

    @Override
    public List<BranchCompanyEntity> getAllByCompany(Long companyId) {
        return branchCompanyRepository.findAllByCompanyEntityId(companyId);
    }

    @Override
    public BranchCompanyEntity getById(Long id) {
        return branchCompanyRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("branch.not.found"));
    }

    @Override
    public BranchCompanyEntity update(Long id, BranchCompanyEntity branch) {
        BranchCompanyEntity existing = getById(id);
        existing.setBranchName(branch.getBranchName());
        existing.setAddress(branch.getAddress());
        existing.setPhone(branch.getPhone());
        return branchCompanyRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        branchCompanyRepository.deleteById(id);
    }
}
