package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.DeclarationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclarationProductRepository extends JpaRepository<DeclarationProduct, Long> {
    List<DeclarationProduct> findAllByDeclarationEntityId(Long declarationId);
}
