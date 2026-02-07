package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.CompanyDocumentDtoRequest;
import com.example.sweezcustoms.dto.request.CompanyDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDocumentDtoResponse;
import com.example.sweezcustoms.dto.view.CompanyDocumentDtoView;
import com.example.sweezcustoms.entity.CompanyDocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompanyDocumentMapper {
    @Autowired
    @Lazy
    protected CompanyMapper companyMapper;
    @Autowired
    @Lazy
    protected UserMapper userMapper;

    abstract CompanyDocumentEntity toEntity(CompanyDocumentDtoRequest companyDocumentDtoRequest);

    @Mapping(target = "companyDtoView", expression = "java(companyMapper.toDtoView(companyDocumentEntity.getCompanyEntity()))")
    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(companyDocumentEntity.getVerifiedBy()))")
    abstract CompanyDocumentDtoResponse toDtoResponse(CompanyDocumentEntity companyDocumentEntity);

    abstract List<CompanyDocumentDtoResponse> toDtoResponseList(List<CompanyDocumentEntity> companyDocumentEntities);

    abstract CompanyDocumentDtoView toDtoView(CompanyDocumentEntity companyDocumentEntity);

    abstract List<CompanyDocumentDtoView> toDtoViewList(List<CompanyDocumentEntity> companyDocumentEntities);
}
