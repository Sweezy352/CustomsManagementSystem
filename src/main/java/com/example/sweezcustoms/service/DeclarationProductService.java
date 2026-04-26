package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.DeclarationProduct;

import java.util.List;

public interface DeclarationProductService {
    DeclarationProduct addToDeclaration(Long declarationId, DeclarationProduct declarationProduct, String tnvedCode);
    DeclarationProduct getById(Long id);
    List<DeclarationProduct> getAllByDeclarationId(Long declarationId);
    DeclarationProduct update(Long id, DeclarationProduct declarationProduct, String tnvedCode);
    void delete(Long id);
}
