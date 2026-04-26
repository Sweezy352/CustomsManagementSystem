package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.BranchCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchCompanyRepository extends JpaRepository<BranchCompanyEntity, Long> {
    List<BranchCompanyEntity> findAllByCompanyEntityId(Long companyId);
}
