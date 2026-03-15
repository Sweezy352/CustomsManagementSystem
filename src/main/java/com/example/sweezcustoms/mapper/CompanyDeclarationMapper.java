package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.CompanyDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDeclarationDtoResponse;
import com.example.sweezcustoms.dto.view.CompanyDeclarationDtoView;
import com.example.sweezcustoms.entity.CompanyDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompanyDeclarationMapper {
    @Lazy
    @Autowired
    protected DeclarationProductMapper declarationProductMapper;

    public abstract CompanyDeclaration toEntity(CompanyDeclarationDtoRequest companyDeclarationDtoRequest);
    @Mapping(target = "declarationProductDtos", expression = "java(declarationProductMapper.toDtoResponseList(companyDeclaration.getDeclarationProducts()))")
    public abstract CompanyDeclarationDtoResponse toDtoResponse(CompanyDeclaration companyDeclaration);
    public abstract List<CompanyDeclarationDtoResponse> toDtoResponseList(List<CompanyDeclaration> companyDeclarations);
    public abstract CompanyDeclarationDtoView toDtoView(CompanyDeclaration companyDeclaration);
    public abstract List<CompanyDeclarationDtoView> toDtoViewList(List<CompanyDeclaration> companyDeclarations);
}
