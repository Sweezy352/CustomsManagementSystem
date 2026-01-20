package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.CompanyDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDtoResponse;
import com.example.sweezcustoms.dto.view.CompanyDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {
    @Autowired
    @Lazy
    protected UserMapper userMapper;
    @Autowired
    @Lazy
    protected CompanyDocumentMapper companyDocumentMapper;
    @Autowired
    @Lazy
    protected DeclarationMapper declarationMapper;



    abstract CompanyEntity toEntity(CompanyDtoRequest companyDtoRequest);

    @Mapping(target = "companyDocumentDtoViews", source = "companyDocuments")
    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(companyEntity.getVerifiedBy()))")
    @Mapping(target = "employees", expression = "java(userMapper.toDtoViewList(companyEntity.getEmployees()))")
    @Mapping(target = "declarationDtoViews", expression = "java(declarationMapper.toDtoViewList(companyEntity.getDeclarationEntities()))")
    abstract CompanyDtoResponse toDtoResponse(CompanyEntity companyEntity);

    abstract List<CompanyDtoResponse> toDtoResponseList(List<CompanyEntity> companyEntities);

    abstract CompanyDtoView toDtoView(CompanyEntity companyEntity);

    abstract List<CompanyDtoView> toDtoViewList(List<CompanyEntity> companyEntities);
}
