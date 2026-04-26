package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.CarDeclaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarDeclarationRepository extends JpaRepository<CarDeclaration, Long> {
    Optional<CarDeclaration> findByVinCode(String vinCode);
    List<CarDeclaration> findAllByUserEntityId(Long userId);
    List<CarDeclaration> findAllByCompanyEntityId(Long companyId);
}
