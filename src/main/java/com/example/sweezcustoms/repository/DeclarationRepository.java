package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.DeclarationEntity;
import com.example.sweezcustoms.enums.DeclarationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeclarationRepository extends JpaRepository<DeclarationEntity, Long> {
    Optional<List<DeclarationEntity>> findByType(DeclarationTypeEnum declarationTypeEnum);

}
