package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.DeclarationProductDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationProductDocumentRepository extends JpaRepository<DeclarationProductDocumentEntity, Long> {

}
