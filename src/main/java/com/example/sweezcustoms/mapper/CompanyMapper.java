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


    public abstract CompanyEntity toEntity(CompanyDtoRequest companyDtoRequest);

    @Mapping(target = "companyDocumentDtoViews", source = "companyDocuments")
    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(companyEntity.getVerifiedBy()))")
    @Mapping(target = "employees", expression = "java(userMapper.toDtoViewList(companyEntity.getEmployees()))")
    @Mapping(target = "declarationDtoViews", expression = "java(declarationMapper.toDtoViewList(companyEntity.getDeclarationEntities()))")
    @Mapping(target = "owner", expression = "java(userMapper.toDtoView(companyEntity.getOwner()))")
    public abstract CompanyDtoResponse toDtoResponse(CompanyEntity companyEntity);

    public abstract List<CompanyDtoResponse> toDtoResponseList(List<CompanyEntity> companyEntities);

    @Mapping(target = "owner", expression = "java(userMapper.toDtoView(companyEntity.getOwner()))")
    public abstract CompanyDtoView toDtoView(CompanyEntity companyEntity);

    public abstract List<CompanyDtoView> toDtoViewList(List<CompanyEntity> companyEntities);

}
