package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.PaymentInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentInvoiceRepository extends JpaRepository<PaymentInvoiceEntity, Long> {
    Optional<PaymentInvoiceEntity> findByDeclarationEntityId(Long declarationId);
}
