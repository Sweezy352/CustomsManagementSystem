package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.DeclarationEntity;
import com.example.sweezcustoms.entity.DeclarationProduct;
import com.example.sweezcustoms.entity.TnvedCodeEntity;
import com.example.sweezcustoms.exceptions.DeclarationNotFoundException;
import com.example.sweezcustoms.exceptions.DeclarationProductNotFoundException;
import com.example.sweezcustoms.repository.DeclarationProductRepository;
import com.example.sweezcustoms.repository.TnvedCodeRepository;
import com.example.sweezcustoms.service.CompanyDeclarationService;
import com.example.sweezcustoms.service.DeclarationProductService;
import com.example.sweezcustoms.service.TnvedCodeService;
import com.example.sweezcustoms.service.UserDeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeclarationProductServiceImpl implements DeclarationProductService {
    private final DeclarationProductRepository declarationProductRepository;
    private final CompanyDeclarationService companyDeclarationService;
    private final UserDeclarationService userDeclarationService;
    private final TnvedCodeService tnvedCodeService;

    @Override
    public DeclarationProduct addToDeclaration(Long declarationId, DeclarationProduct product, String tnvedCode) {
        DeclarationEntity declaration = resolveDeclaration(declarationId);
        product.setDeclarationEntity(declaration);
        applyTnvedAndCalculateTaxes(product, tnvedCode);
        return declarationProductRepository.save(product);
    }

    @Override
    public DeclarationProduct getById(Long id) {
        return declarationProductRepository.findById(id)
                .orElseThrow(() -> new DeclarationProductNotFoundException("declaration.product.not.found"));
    }

    @Override
    public List<DeclarationProduct> getAllByDeclarationId(Long declarationId) {
        return declarationProductRepository.findAllByDeclarationEntityId(declarationId);
    }

    @Override
    public DeclarationProduct update(Long id, DeclarationProduct updated, String tnvedCode) {
        DeclarationProduct existing = getById(id);
        existing.setProductName(updated.getProductName());
        existing.setProductDescription(updated.getProductDescription());
        existing.setProductMaterials(updated.getProductMaterials());
        existing.setQuantity(updated.getQuantity());
        existing.setUnitType(updated.getUnitType());
        existing.setWeightNetto(updated.getWeightNetto());
        existing.setWeightBrutto(updated.getWeightBrutto());
        existing.setPricePerUnit(updated.getPricePerUnit());
        existing.setTotalPrice(updated.getTotalPrice());
        applyTnvedAndCalculateTaxes(existing, tnvedCode);
        return declarationProductRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        declarationProductRepository.deleteById(id);
    }

    // Пробуем найти декларацию сначала среди компаний, потом среди физлиц
    private DeclarationEntity resolveDeclaration(Long declarationId) {
        try {
            return companyDeclarationService.getDeclarationById(declarationId);
        } catch (Exception e) {
            try {
                return userDeclarationService.getDeclarationById(declarationId);
            } catch (Exception ex) {
                throw new DeclarationNotFoundException("declaration.not.found");
            }
        }
    }

    // Привязываем TnvedCode и рассчитываем налоги от totalPrice
    private void applyTnvedAndCalculateTaxes(DeclarationProduct product, String tnvedCode) {
        if (tnvedCode == null || tnvedCode.isBlank()) return;

        TnvedCodeEntity tnved = tnvedCodeService.getByCode(tnvedCode);
        product.setTnvedCode(tnved);

        BigDecimal base = product.getTotalPrice();
        if (base == null) return;

        product.setNds(
                base.multiply(tnved.getDefaultNdsRate())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
        product.setCustomsDuty(
                base.multiply(tnved.getDefaultCustomsDutyRate())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
        product.setExcise(
                base.multiply(tnved.getDefaultExciseRate())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
    }
}
