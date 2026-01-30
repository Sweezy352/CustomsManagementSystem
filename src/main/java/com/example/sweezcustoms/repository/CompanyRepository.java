package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.enums.CustomsStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByName(String name);
    Optional<List<CompanyEntity>> findByStatus(CustomsStatusEnum status);

    Optional<CompanyEntity> findByTin(String tin);

    Optional<CompanyEntity> findByOkpo(String okpo);

    Optional<CompanyEntity> findByCustomsCode(String customsCode);
}
