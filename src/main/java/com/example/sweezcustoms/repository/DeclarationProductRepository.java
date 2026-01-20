package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.DeclarationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeclarationProductRepository extends JpaRepository<DeclarationProductEntity, Long> {
    Optional<DeclarationProductEntity> findByName(String name);
    Optional<List<DeclarationProductEntity>> findByDeclarationEntityId(Long id);
}
