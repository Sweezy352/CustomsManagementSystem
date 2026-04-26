package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.CompanyDeclaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDeclarationRepository extends JpaRepository<CompanyDeclaration, Long> {
}
